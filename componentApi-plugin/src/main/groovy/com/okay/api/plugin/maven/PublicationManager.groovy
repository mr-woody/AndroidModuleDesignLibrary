package com.okay.api.plugin.maven

import com.okay.api.plugin.extension.Publication
import com.okay.api.plugin.utils.ModuleApiUtil
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class PublicationManager {

    private static PublicationManager sPublicationManager

    private File apiDir
    private Map<String, Publication> publicationManifest

    Digraph<String> dependencyGraph
    Map<String, Publication> publicationDependencies

    static getInstance() {
        if (sPublicationManager == null) {
            sPublicationManager = new PublicationManager()
        }
        return sPublicationManager
    }

    void loadManifest(Project rootProject, File apiDir) {
        this.apiDir = apiDir

        publicationManifest = new HashMap<>()
        dependencyGraph = new Digraph<String>()
        publicationDependencies = new HashMap<>()

        rootProject.gradle.buildFinished {
            if (it.failure != null) {
                return
            }
            saveManifest()
        }

        File publicationManifest = new File(apiDir, 'publicationManifest.xml')
        if (!publicationManifest.exists()) {
            return
        }

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance()
        Document document = builderFactory.newDocumentBuilder().parse(publicationManifest)
        NodeList publicationNodeList = document.documentElement.getElementsByTagName("publication")
        for (int i = 0; i < publicationNodeList.getLength(); i++) {
            Element publicationElement = (Element) publicationNodeList.item(i)

            Publication publication = new Publication()
            publication.project = publicationElement.getAttribute("project")
            publication.groupId = publicationElement.getAttribute("groupId")
            publication.artifactId = publicationElement.getAttribute("artifactId")
            publication.version = publicationElement.getAttribute("version")
            if (publication.version == "") publication.version = null
            publication.invalid = Boolean.valueOf(publicationElement.getAttribute("invalid"))

            if (!publication.invalid) {
                NodeList sourceSetNodeList = publicationElement.getElementsByTagName("sourceSet")
                Element sourceSetElement = (Element) sourceSetNodeList.item(0)
                SourceSet sourceSet = new SourceSet()
                sourceSet.path = sourceSetElement.getAttribute("path")
                sourceSet.lastModifiedSourceFile = new HashMap<>()
                NodeList fileNodeList = sourceSetElement.getElementsByTagName("file")
                for (int k = 0; k < fileNodeList.getLength(); k++) {
                    Element fileElement = (Element) fileNodeList.item(k)
                    SourceFile sourceFile = new SourceFile()
                    sourceFile.path = fileElement.getAttribute("path")
                    sourceFile.lastModified = fileElement.getAttribute("lastModified").toLong()
                    sourceSet.lastModifiedSourceFile.put(sourceFile.path, sourceFile)
                }
                publication.apiSourceSet = sourceSet
            }

            this.publicationManifest.put(publication.groupId + '-' + publication.artifactId, publication)
        }

    }

    private void saveManifest() {
        if (!apiDir.exists()) {
            apiDir.mkdirs()
        }
        File publicationManifest = new File(apiDir, 'publicationManifest.xml')

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance()
        Document document = builderFactory.newDocumentBuilder().newDocument()
        Element manifestElement = document.createElement("manifest")
        this.publicationManifest.each {
            Publication publication = it.value
            if (!publication.hit || publication.invalid) return

            Element publicationElement = document.createElement('publication')
            publicationElement.setAttribute('project', publication.project)
            publicationElement.setAttribute('groupId', publication.groupId)
            publicationElement.setAttribute('artifactId', publication.artifactId)
            publicationElement.setAttribute('version', publication.version)
            publicationElement.setAttribute('invalid', publication.invalid ? "true" : "false")

            if (!publication.invalid) {
                Element sourceSetElement = document.createElement('sourceSet')
                sourceSetElement.setAttribute('path', publication.apiSourceSet.path)
                publication.apiSourceSet.lastModifiedSourceFile.each {
                    SourceFile sourceFile = it.value
                    Element sourceFileElement = document.createElement('file')
                    sourceFileElement.setAttribute('path', sourceFile.path)
                    sourceFileElement.setAttribute('lastModified', sourceFile.lastModified.toString())
                    sourceSetElement.appendChild(sourceFileElement)
                }
                publicationElement.appendChild(sourceSetElement)
            }
            manifestElement.appendChild(publicationElement)
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(new DOMSource(manifestElement), new StreamResult(publicationManifest))
    }

    void addDependencyGraph(Publication publication) {
        def key = publication.groupId + '-' + publication.artifactId
        publicationDependencies.put(key, publication)
        dependencyGraph.add(key)
        if (publication.dependencies != null) {
            if (publication.dependencies.implementation != null) {
                publication.dependencies.implementation.each {
                    if (it instanceof String && it.startsWith('api-')) {
                        String[] gav = ModuleApiUtil.filterGAV(it.replace('api-', ''))
                        dependencyGraph.add(key, gav[0] + '-' + gav[1])
                        if (!dependencyGraph.isDag()) {
                            def apiPublication = gav[0] + ':' + gav[1] + (gav[2] == null ? (":" + gav[2]) : "")
                            throw new RuntimeException("Circular dependency between api publication '${publication.groupId}:${publication.artifactId}' and '${apiPublication}'.")
                        }
                    }
                }
            }

            if (publication.dependencies.compileOnly != null) {
                publication.dependencies.compileOnly.each {
                    if (it instanceof String && it.startsWith('api-')) {
                        String[] gav = ModuleApiUtil.filterGAV(it.replace('api-', ''))
                        dependencyGraph.add(key, gav[0] + '-' + gav[1])
                        if (!dependencyGraph.isDag()) {
                            def apiPublication = gav[0] + ':' + gav[1] + (gav[2] == null ? (":" + gav[2]) : "")
                            throw new RuntimeException("Circular dependency between api publication '${publication.groupId}:${publication.artifactId}' and '${apiPublication}'.")
                        }
                    }
                }
            }
        }
    }

    boolean hasModified(Publication publication) {
        Publication lastPublication = publicationManifest.get(publication.groupId + '-' + publication.artifactId)
        if (lastPublication == null) {
            return true
        }

        if (publication.invalid != lastPublication.invalid) {
            return true
        }

        return hasModifiedSourceSet(publication.apiSourceSet, lastPublication.apiSourceSet)
    }

    private boolean hasModifiedSourceSet(SourceSet sourceSet1, SourceSet sourceSet2) {
        return hasModifiedSourceFile(sourceSet1.lastModifiedSourceFile, sourceSet2.lastModifiedSourceFile)
    }

    private boolean hasModifiedSourceFile(Map<String, SourceFile> map1, Map<String, SourceFile> map2) {
        if (map1.size() != map2.size()) {
            return true
        }
        for (Map.Entry<String, SourceFile> entry1 : map1.entrySet()) {
            SourceFile sourceFile1 = entry1.getValue()
            SourceFile sourceFile2 = map2.get(entry1.getKey())
            if (sourceFile2 == null) {
                return true
            }
            if (sourceFile1.lastModified != sourceFile2.lastModified) {
                return true
            }
        }
        return false
    }

    void addPublication(Publication publication) {
        publicationManifest.put(publication.groupId + '-' + publication.artifactId, publication)
    }

    Publication getPublication(String groupId, String artifactId) {
        return publicationManifest.get(groupId + '-' + artifactId)
    }

    Publication getPublicationByKey(String key) {
        return publicationManifest.get(key)
    }

    List<Publication> getPublicationByProject(Project project) {
        String displayName = project.getDisplayName()
        String projectName = displayName.substring(displayName.indexOf("'") + 1, displayName.lastIndexOf("'"))

        List<Publication> publications = new ArrayList<>()
        publicationManifest.each {
            if (projectName == it.value.project) {
                publications.add(it.value)
            }
        }
        return publications
    }

    void hitPublication(Publication publication) {
        Publication existsPublication = publicationManifest.get(publication.groupId + '-' + publication.artifactId)
        if (existsPublication == null) return

        if (existsPublication.hit) {
            validPublication(publication, existsPublication)
        } else {
            existsPublication.hit = true
        }
    }

    private void validPublication(Publication publication, Publication existsPublication) {
        if (publication.project != existsPublication.project) {
            throw new GradleException("Already exists publication " + existsPublication.groupId + ":" + existsPublication.artifactId + " in project '${existsPublication.project}'.")
        }
    }

}