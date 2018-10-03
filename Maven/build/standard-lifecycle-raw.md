# Standard lifecycles in Maven

More details about Maven lifecycles can be found at http://maven.apache.org/ref/3.2.3/maven-core/lifecycles.html.

!TOC

Maven comes with three standard lifecycles: `default`, `clean`, and `site`. Each lifecycle defines its own set of phases. You cannot define **the same phase** in **two different lifecycles**.

> 这段理解三个意思：  
> （1）Maven提供了三种标准的生命周期：default、clean和site。  
> （2）每个lifecycle有自己的phase。  
> （3）在不同的lifecycle中，不能定义同名的phase。


## The clean lifecycle

The `clean` lifecycle defines three phases: `pre-clean` , `clean` , and `post-clean` .

!INCLUDE "clean-lifecycle-phases-short.mdpp"


## The default lifecycle

The `default` lifecycle in Maven defines **23 phases**. 

!INCLUDE "default-lifecycle-phases-short.mdpp"


> You cannot define **the same phase** in **two different lifecycles**.



The following points summarize all the phases defined under the `default`
lifecycle in their order of execution:

!INCLUDE "default-lifecycle-phases-long.mdpp"



## The site lifecycle

The `site` lifecycle is defined with four phases: `pre-site` , `site` , `post-site` , and `site-deploy` . 

- The site lifecycle
    - pre-site
    - site
    - post-site
    - site-deploy


