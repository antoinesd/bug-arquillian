package org.jboss.bug;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;

@RunWith(Arquillian.class)
public class HelloWorldTest {

    @Inject
    HelloWorld hello;

   /* @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {


        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClass(HelloWorld.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return ret;
    }*/

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.apache.deltaspike.core:deltaspike-core-impl")
                .withTransitivity().asFile();

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClass(HelloWorld.class)
                .addAsLibraries(libs)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return ret;
    }

    @Test
    public void helloTest() {
        Assert.assertEquals(hello.sayHello(), "Hello World");
    }


}
