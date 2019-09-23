package com.okay.component.compiler;

import com.okay.component.annotation.LifeCycleConfig;
import com.okay.component.annotation.RegistApplication;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

public class ApplicationProcessor extends AbstractProcessor {
    public static final String API_INTERFACE_CLASS_NAME = "com.okay.component.api.IApplicationLife";

    private Messager mMessager;
    private Filer mFiler;
    private Elements mElements;


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RegistApplication.class);
        if (elements.size() == 0) {
            return false;
        }
        for (Element element : elements) {
            if (!element.getKind().isClass()) {
                throw new RuntimeException("Annotation RegistApplication can only be used in class.");
            }
            TypeElement typeElement = (TypeElement) element;

            //这里检查一下，使用了该注解的类，同时必须要实现IApplicationLife接口，否则会报错
            List<? extends TypeMirror> mirrorList = typeElement.getInterfaces();
            if (mirrorList.isEmpty()) {
                throw new RuntimeException(typeElement.getQualifiedName() + " must implements interface " + API_INTERFACE_CLASS_NAME);
            }
            boolean checkInterfaceFlag = false;
            for (TypeMirror mirror : mirrorList) {
                if (API_INTERFACE_CLASS_NAME.equals(mirror.toString())) {
                    checkInterfaceFlag = true;
                }
            }
            if (!checkInterfaceFlag) {
                throw new RuntimeException(typeElement.getQualifiedName() + " must implements interface " + API_INTERFACE_CLASS_NAME);
            }

            String targetClassName = typeElement.getQualifiedName().toString();
            String toClassName = targetClassName.replaceAll("\\.", "\\$\\$");

            TypeSpec typeSpec = TypeSpec.classBuilder(toClassName).addModifiers(new Modifier[]{Modifier.PUBLIC}).build();
            JavaFile javaFile = JavaFile.builder(LifeCycleConfig.APT_CLASS_PACKAGE_NAME, typeSpec).build();
            try {
                javaFile.writeTo(this.mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.mMessager = processingEnv.getMessager();
        this.mFiler = processingEnv.getFiler();
        this.mElements = processingEnv.getElementUtils();
    }
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet();
        annotations.add(RegistApplication.class.getCanonicalName());
        return annotations;
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}
