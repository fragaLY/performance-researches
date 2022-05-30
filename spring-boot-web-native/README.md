### SPRING BOOT WEB NATIVE PERFORMANCE REPORT

> Model Name: MacBook Pro

> Model Identifier: MacBookPro16,2

> Processor Name: Quad-Core Intel Core i7

> Processor Speed: 2,3 GHz

> Number of Processors: 1

> Total Number of Cores: 4

> L2 Cache (per Core): 512 KB

> L3 Cache: 8 MB

> Hyper-Threading Technology: Enabled

> Memory:	32 GB

#### Base Setup

|JDK|GC|Gradle|Spring Boot|Spring AOT|
|:--|:-|:-----|:----------|:---------|
|17 |G1|7.4.1 |2.7.0      |0.12.0    |

####  OVERALL

Average build time between x-x minutes.

|SERVER  |BOOT UP (s)|ACTIVE USERS|RPS    |SATURATION POINT|JVM HEAP (MB)|JVM NON-HEAP (MB)|JVM CPU (%)|THREADS (MAX)|POSTGRES CPU (%)|
|:-------|:----------|:-----------|:------|:---------------|:------------|:----------------|:----------|:------------|:---------------|
|UNDERTOW|3,59       |10221       |426.859|1709            |658          |94               |11         |33           |99              |

* UNDERTOW

``` yaml
server:
 compression:
   enabled: true
 undertow:
   threads:
     io: 4 # default: equals to cores count
     worker: 8 # default: 8

```

 ![](./static/undertow.png)