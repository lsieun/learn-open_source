# Gson #

Gson User Guide: [https://github.com/google/gson/blob/master/UserGuide.md](https://github.com/google/gson/blob/master/UserGuide.md)

Overview of the Gson API: [http://www.studytrails.com/java/json/java-google-json-introduction/](http://www.studytrails.com/java/json/java-google-json-introduction/)

Gson Tutorial: [https://www.tutorialspoint.com/gson/gson_overview.htm](https://www.tutorialspoint.com/gson/gson_overview.htm)

对于null值的特殊处理 


There are **two ways** to convert json to java.

- **Using the `com.google.gson.Gson` class**. Create a new instance of this class and use the method `public <T> T fromJson(String json, Class<T> classOfT)`. classOfT is the java object to which the json is to be converted to.
- **The other way is to use the `com.google.gson.GsonBuilder` class**. This class allows **setting up certain features**, such as – *allowing null serialization* or *setting up custom serializing policies*. Create a GsonBuilder, apply the features and then obtain the Gson class from the builder.

GsonBuilder provides a lot of other customization. You can disable HTML escaping, exclude fields with specific modifiers (e.g. exclude all protected fields), set custom type adapters, set exclusion policies etc. Look at the GsonBuilder JavaDoc for the complete list.

We use the `JsonReader` class to read the json as a stream of tokens. The beginning of an object or an array is also a token. 


## GsonBuilder ##

Use this builder to construct a `Gson` instance when you need to set configuration options other than the default. For `Gson` with default configuration, it is simpler to use `new Gson()`. `GsonBuilder` is best used by **creating it**, and then **invoking its various configuration methods**, and finally **calling create**.

> GsonBuilder 可以提供更多的configuration options。
> 使用GsonBuilder的步骤是：（1）创建GsonBuilder；（2）调用GsonBuilder的configuration method；（3）调用GsonBuilder的create方法

The following is an example shows how to use the `GsonBuilder` to construct a Gson instance:

	Gson gson = new GsonBuilder()
		.registerTypeAdapter(Id.class, new IdTypeAdapter())
		.enableComplexMapKeySerialization()
		.serializeNulls()
		.setDateFormat(DateFormat.LONG)
		.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
		.setPrettyPrinting()
		.setVersion(1.0)
		.create();

**NOTES**:  
- the order of invocation of configuration methods does not matter.  
- The default serialization of `Date` and its subclasses in Gson does not contain time-zone information. So, if you are using date/time instances, use `GsonBuilder` and its `setDateFormat` methods.











