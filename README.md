# Simple Config Worker (SCW) 
This is a library that allows to easily work with configuration files in JSON format

<!-- TOC -->
* [Simple Config Worker (SCW)](#simple-config-worker--scw-)
  * [Download (Gradle)](#download--gradle-)
  * [Usage](#usage)
    * [Save](#save)
    * [Load And Overwrite](#load-and-overwrite)
    * [Config Node As Class](#config-node-as-class)
    * [Edit Class-Node](#edit-class-node)
  * [Problems And Limitations](#problems-and-limitations)
<!-- TOC -->

## Download (Gradle)

```
repositories {
    mavenCentral()

    // SCW
    maven {
        url 'https://lab.knrg.su/api/v4/projects/28/packages/maven'
    }
}

dependencies {
    implementation 'com.google.code.gson:gson:2.10.1'
    implementation "su.knst.scw:scw:0.2.2"
}
```

## Usage

### Save

```
File file = new File("main.conf");
RootConfig rootConfig = new RootConfig(file);

rootConfig.setValue("someValue", Math.random()); // set value

ConfigNode misc = rootConfig.subNode("misc");
misc.setValue("url", "https://example.com"); // write to subnode

rootConfig.save(); // save all
```

Calling `rootConfig.save()` saves all changes from all nodes.

### Load And Overwrite

```
File file = new File("main.conf");
RootConfig rootConfig = new RootConfig(file);
rootConfig.load();

double value = rootConfig.getDouble("someValue").orElse(0d); // get saved value or 0

rootConfig.setValue("someValue", Math.random()); // overwrite old value 

rootConfig.save(); // save it
```

Calling `rootConfig.load()` load all config to memory.

Use several configuration files if you plan a huge variety of parameters

### Config Node As Class

```
ConfigNode someNode = rootConfig.subNode("class");

someNode.setAs(new TestClass()); // now the node "class" has values from the object

rootConfig.save() // save changes

someNode.getAs(TestClass.class).orElse(new TestClass()); // get object with values from node or default
```

Architecturally, the node has a separate parameter with JsonElement type for storing an object inside itself
Calling `someNode.setAs()` clear node and set JsonElement parameter.

### Edit Class-Node

``` 
TestClass object = someNode.getAs(TestClass.class)
                           .orElse(new TestClass());

double value = object.variableName;
object.variableName = 123d;

someNode.setAs(object);
rootConfig.save();
```

## Problems And Limitations

1. All parameters not from objects are translated to String. Therefore, to store more complex structures, such as arrays, you need to use separate objects
2. There is no handling of cases where the configuration has been changed externally. Although initially the library was created to read the configuration only at startup and save changes (example config) at shutdown
3. Without additional technical values in the config, it is impossible to determine whether the node was saved as a class. Therefore, it is not worth using the usual methods for obtaining values for class-nodes. And `getAs()` will not work at all for normal nodes.
4. The library has been tested in normal scenarios, but this does not exclude all errors. Please send bug reports here: me@knst.su (en/ru)