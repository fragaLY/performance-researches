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

* OVERALL
|BOOT UP (s)|ACTIVE USERS|SATURATION POINT|JVM HEAP (MB)|JVM NON-HEAP (MB)|JVM CPU (%)|THREADS (MAX)|POSTGRES CPU (%)|
|:----------|:-----------|:---------------|:------------|:----------------|:----------|:------------|:---------------|
|           |8250        |1385            |350          |94               |27         |230          |90              |

* BEFORE SATURATION POINT
|RESPONSE TIME MAX (ms)|REQUESTS PER SECOND|RESPONSES PER SECOND|
|:---------------------|:------------------|:-------------------|
|50                    |499                |499                 |

* AFTER SATURATION POINT
|RESPONSE TIME MAX [99th pct] (ms)|REQUESTS PER SECOND|RESPONSES PER SECOND|
|:--------------------------------|:------------------|:-------------------|
|25968                            |684                |527                 |

* Tomcat Tuning

``` yaml
server:
  tomcat:
    accept-count: 100 # default: 100
    threads:
      max: 100 # default: 200
      min-spare: 10 # default: 10

```

* OVERALL
|BOOT UP (s)|ACTIVE USERS|SATURATION POINT|JVM HEAP (MB)|JVM NON-HEAP (MB)|JVM CPU (%)|THREADS (MAX)|POSTGRES CPU (%)|
|:----------|:-----------|:---------------|:------------|:----------------|:----------|:------------|:---------------|
|3,94       |8250        |1730            |311          |94               |18         |128          |90              |

* BEFORE SATURATION POINT
|RESPONSE TIME MAX (ms)|REQUESTS PER SECOND|RESPONSES PER SECOND|
|:---------------------|:------------------|:-------------------|
|30                    |649                |648                 |

* AFTER SATURATION POINT
|RESPONSE TIME MAX [99th pct] (ms)|REQUESTS PER SECOND|RESPONSES PER SECOND|
|:--------------------------------|:------------------|:-------------------|
|52215                            |681                |423                 |

* Jetty Tuning

``` yaml
server:
  tomcat:
    accept-count: 100 # default: 100
    threads:
      max: 100 # default: 200
      min-spare: 10 # default: 10

```

* Undertow Tuning