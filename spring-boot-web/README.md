### SPRING BOOT WEB PERFORMANCE REPORT

Model Name:	MacBook Pro
Model Identifier: MacBookPro16,2
Processor Name:	Quad-Core Intel Core i7
Processor Speed: 2,3 GHz
Number of Processors: 1
Total Number of Cores: 4
L2 Cache (per Core): 512 KB
L3 Cache: 8 MB
Hyper-Threading Technology:	Enabled
Memory:	32 GB

* Initial Setup

* Base Setup *
|JDK|GC|Server|Gradle|
|:--|:-|:-----|:-----|
|17 |G1|Tomcat|7.4.1 |

* Tomcat

``` yaml
server:
 compression:
   enabled: true
 tomcat:
 accept-count: 100 # default: 100
 threads:
   max: 200 # default: 200
   min-spare: 10 # default: 10

```

* OVERALL
|BOOT UP (s)|ACTIVE USERS|RPS    |SATURATION POINT|JVM HEAP (MB)|JVM NON-HEAP (MB)|JVM CPU (%)|THREADS (MAX)|POSTGRES CPU (%)|
|:----------|:-----------|:------|:---------------|:------------|:----------------|:----------|:------------|:---------------|
|3,94       |8250        |436,715|1730            |311          |94               |18         |225          |90              |

* Jetty

``` yaml
server:
 compression:
   enabled: true
 jetty:
   threads:
     max: 200 # default
     min: 8 # default

```

* OVERALL
|BOOT UP (s)|ACTIVE USERS|RPS    |SATURATION POINT|JVM HEAP (MB)|JVM NON-HEAP (MB)|JVM CPU (%)|THREADS (MAX)|POSTGRES CPU (%)|
|:----------|:-----------|:------|:---------------|:------------|:----------------|:----------|:------------|:---------------|
|3,83       |10224       |429,825|1463            |853          |94               |16         |223          |90              |

* Undertow

``` yaml
server:
 compression:
   enabled: true
 jetty:
   threads:
     max: 200 # default
     min: 8 # default

```

* OVERALL
|BOOT UP (s)|ACTIVE USERS|RPS    |SATURATION POINT|JVM HEAP (MB)|JVM NON-HEAP (MB)|JVM CPU (%)|THREADS (MAX)|POSTGRES CPU (%)|
|:----------|:-----------|:------|:---------------|:------------|:----------------|:----------|:------------|:---------------|
|3,83       |10224       |429,825|1463            |829          |94               |16         |223          |90              |
