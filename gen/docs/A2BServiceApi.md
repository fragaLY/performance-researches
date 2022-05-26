# A2BServiceApi

All URIs are relative to *http://localhost:8080/api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**assignTransferToUser**](A2BServiceApi.md#assignTransferToUser) | **POST** /users/{userId}/transfers{transferId} | Assign the transfer to user
[**citiesRetrieving**](A2BServiceApi.md#citiesRetrieving) | **GET** /countries/{id}/cities | Get all cities for country in system
[**countriesRetrieving**](A2BServiceApi.md#countriesRetrieving) | **GET** /countries | Get all countries in system
[**locationsRetrieving**](A2BServiceApi.md#locationsRetrieving) | **GET** /countries/{countryId}/cities/{cityId}/locations | Get all cities locations for country in system
[**transfersRetrieving**](A2BServiceApi.md#transfersRetrieving) | **GET** /transfers | Get all transfers for selected origin, destination, date
[**updateUserProfile**](A2BServiceApi.md#updateUserProfile) | **PUT** /users/{userId} | Update user&#39;s profile
[**updateUserTransfer**](A2BServiceApi.md#updateUserTransfer) | **PUT** /users/{userId}/transfers{transferId} | Update user&#39;s transfer
[**userProfileRetrieving**](A2BServiceApi.md#userProfileRetrieving) | **GET** /users/{userId} | Get user&#39;s profile
[**userTransferRetrieving**](A2BServiceApi.md#userTransferRetrieving) | **GET** /users/{userId}/transfers{transferId} | Get user&#39;s transfer
[**userTransfersRetrieving**](A2BServiceApi.md#userTransfersRetrieving) | **GET** /users/{userId}/transfers | Get user&#39;s transfers


<a name="assignTransferToUser"></a>
# **assignTransferToUser**
> assignTransferToUser(userId, transferId, transferAssigning)

Assign the transfer to user

Assign the transfer to user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long userId = 56L; // Long | Id of user
    Long transferId = 56L; // Long | Id of transfer
    TransferAssigning transferAssigning = new TransferAssigning(); // TransferAssigning | 
    try {
      apiInstance.assignTransferToUser(userId, transferId, transferAssigning);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#assignTransferToUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**| Id of user |
 **transferId** | **Long**| Id of transfer |
 **transferAssigning** | [**TransferAssigning**](TransferAssigning.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json;charset=UTF-8
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Transfer had been assigned |  -  |
**400** | Bad Request |  -  |
**404** | Challenge not found |  -  |
**500** | General application error |  -  |

<a name="citiesRetrieving"></a>
# **citiesRetrieving**
> List&lt;Object&gt; citiesRetrieving(id)

Get all cities for country in system

Get all cities for country. Cities are retrieved.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long id = 56L; // Long | Id of country
    try {
      List<Object> result = apiInstance.citiesRetrieving(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#citiesRetrieving");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| Id of country |

### Return type

**List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Cities details |  -  |
**400** | Bad request |  -  |
**500** | General application error |  -  |

<a name="countriesRetrieving"></a>
# **countriesRetrieving**
> List&lt;Object&gt; countriesRetrieving()

Get all countries in system

Get all countries. Countries are retrieved.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    try {
      List<Object> result = apiInstance.countriesRetrieving();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#countriesRetrieving");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | List of countries |  -  |
**500** | General application error |  -  |

<a name="locationsRetrieving"></a>
# **locationsRetrieving**
> List&lt;Object&gt; locationsRetrieving(countryId, cityId)

Get all cities locations for country in system

Get all cities locations for country. Locations are retrieved.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long countryId = 56L; // Long | Id of country
    Long cityId = 56L; // Long | Id of city
    try {
      List<Object> result = apiInstance.locationsRetrieving(countryId, cityId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#locationsRetrieving");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **countryId** | **Long**| Id of country |
 **cityId** | **Long**| Id of city |

### Return type

**List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Locations details |  -  |
**400** | Bad request |  -  |
**500** | General application error |  -  |

<a name="transfersRetrieving"></a>
# **transfersRetrieving**
> List&lt;Object&gt; transfersRetrieving(originId, destinationId, date)

Get all transfers for selected origin, destination, date

Get all available transfers. Transfers are retrieved.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long originId = 56L; // Long | Id of origin
    Long destinationId = 56L; // Long | Id of destination
    LocalDate date = new LocalDate(); // LocalDate | Date of transfer
    try {
      List<Object> result = apiInstance.transfersRetrieving(originId, destinationId, date);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#transfersRetrieving");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **originId** | **Long**| Id of origin |
 **destinationId** | **Long**| Id of destination |
 **date** | **LocalDate**| Date of transfer |

### Return type

**List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Transfers for selected origin, destination, date |  -  |
**400** | Bad request |  -  |
**500** | General application error |  -  |

<a name="updateUserProfile"></a>
# **updateUserProfile**
> updateUserProfile(userId, userProfileEdition)

Update user&#39;s profile

Update user&#39;s profile

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long userId = 56L; // Long | Id of user
    UserProfileEdition userProfileEdition = new UserProfileEdition(); // UserProfileEdition | 
    try {
      apiInstance.updateUserProfile(userId, userProfileEdition);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#updateUserProfile");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**| Id of user |
 **userProfileEdition** | [**UserProfileEdition**](UserProfileEdition.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json;charset=UTF-8
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**204** | User profile updated |  -  |
**400** | Bad Request |  -  |
**404** | User not found |  -  |
**500** | General application error |  -  |

<a name="updateUserTransfer"></a>
# **updateUserTransfer**
> updateUserTransfer(userId, transferId, userTransferEdition)

Update user&#39;s transfer

Update user&#39;s transfer

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long userId = 56L; // Long | Id of user
    Long transferId = 56L; // Long | Id of transfer
    UserTransferEdition userTransferEdition = new UserTransferEdition(); // UserTransferEdition | 
    try {
      apiInstance.updateUserTransfer(userId, transferId, userTransferEdition);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#updateUserTransfer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**| Id of user |
 **transferId** | **Long**| Id of transfer |
 **userTransferEdition** | [**UserTransferEdition**](UserTransferEdition.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json;charset=UTF-8
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**204** | User transfer updated |  -  |
**400** | Bad Request |  -  |
**404** | User not found |  -  |
**500** | General application error |  -  |

<a name="userProfileRetrieving"></a>
# **userProfileRetrieving**
> UserProfile userProfileRetrieving(userId)

Get user&#39;s profile

Get user&#39;s profile. User&#39;s profile is retrieved.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long userId = 56L; // Long | Id of user
    try {
      UserProfile result = apiInstance.userProfileRetrieving(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#userProfileRetrieving");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**| Id of user |

### Return type

[**UserProfile**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | User&#39;s profile |  -  |
**400** | Bad request |  -  |
**500** | General application error |  -  |

<a name="userTransferRetrieving"></a>
# **userTransferRetrieving**
> UserTransfer userTransferRetrieving(userId, transferId)

Get user&#39;s transfer

Get user&#39;s transfer. User&#39;s transfer is retrieved.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long userId = 56L; // Long | Id of user
    Long transferId = 56L; // Long | Id of transfer
    try {
      UserTransfer result = apiInstance.userTransferRetrieving(userId, transferId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#userTransferRetrieving");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**| Id of user |
 **transferId** | **Long**| Id of transfer |

### Return type

[**UserTransfer**](UserTransfer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | User&#39;s transfers |  -  |
**400** | Bad request |  -  |
**500** | General application error |  -  |

<a name="userTransfersRetrieving"></a>
# **userTransfersRetrieving**
> List&lt;Object&gt; userTransfersRetrieving(userId)

Get user&#39;s transfers

Get user&#39;s transfers. User&#39;s transfers are retrieved.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.A2BServiceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/api/v1");

    A2BServiceApi apiInstance = new A2BServiceApi(defaultClient);
    Long userId = 56L; // Long | Id of user
    try {
      List<Object> result = apiInstance.userTransfersRetrieving(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling A2BServiceApi#userTransfersRetrieving");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **Long**| Id of user |

### Return type

**List&lt;Object&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | User&#39;s transfers |  -  |
**400** | Bad request |  -  |
**500** | General application error |  -  |

