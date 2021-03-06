[![Github Action](https://github.com/jericks/geoscript-groovy-cookbook/workflows/Build/badge.svg)](https://github.com/jericks/geoscript-groovy-cookbook/actions)

GeoScript Groovy Cookbook
=========================
The GeoScript Groovy Cookbook contains short recipes showing how to use the GeoScript Groovy API.

[Web Site](https://jericks.github.io/geoscript-groovy-cookbook/)

[PDF](https://jericks.github.io/geoscript-groovy-cookbook/index.pdf)

Key Features
------------
1. Cookbook with Recipes
2. Code is unit tested
3. Images are generated by unit tests
4. Version specific

Adding Recipes
--------------
1. Add code to an existing recipes or create a new group of recipes in **src/main/groovy**.

    The code for your recipe should be surrounded by special comments:

    ```
    // tag::createPoint[]
    Point point = new Point(-123,46)
    // end::createPoint[]
    ```

    And if the recipe is visual please create an image in the **src/docs/asciidoc/images** directory.

    If the recipe outputs any text please save the file in the **src/docs/asciidoc/output** directory.

2. Second, add a test for your recipe so all code snippets are tested in **src/test/groovy**.

3. Finally, add your recipe to an assciidoc file in the **src/docs/asciidoc** directory.

Build
-----

```
mvn clean install
```

The generated web site can be found in the **target/generated-docs** directory.