/*
 * A2B Service
 * API Specification for A2B Service. Goal of service is to aggregate all a2b logic.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: vadzim.kavalkou@gmail.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.BadRequestException;
import org.threeten.bp.LocalDate;
import org.openapitools.client.model.NotFoundException;
import org.openapitools.client.model.ServerException;
import org.openapitools.client.model.TransferAssigning;
import org.openapitools.client.model.UserProfile;
import org.openapitools.client.model.UserProfileEdition;
import org.openapitools.client.model.UserTransfer;
import org.openapitools.client.model.UserTransferEdition;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for A2BServiceApi
 */
@Ignore
public class A2BServiceApiTest {

    private final A2BServiceApi api = new A2BServiceApi();


    /**
     * Assign the transfer to user
     * <p>
     * Assign the transfer to user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void assignTransferToUserTest() throws ApiException {
        Long userId = null;
        Long transferId = null;
        TransferAssigning transferAssigning = null;
        api.assignTransferToUser(userId, transferId, transferAssigning);

        // TODO: test validations
    }

    /**
     * Get all cities for country in system
     * <p>
     * Get all cities for country. Cities are retrieved.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void citiesRetrievingTest() throws ApiException {
        Long id = null;
        List<Object> response = api.citiesRetrieving(id);

        // TODO: test validations
    }

    /**
     * Get all countries in system
     * <p>
     * Get all countries. Countries are retrieved.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void countriesRetrievingTest() throws ApiException {
        List<Object> response = api.countriesRetrieving();

        // TODO: test validations
    }

    /**
     * Get all cities locations for country in system
     * <p>
     * Get all cities locations for country. Locations are retrieved.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void locationsRetrievingTest() throws ApiException {
        Long countryId = null;
        Long cityId = null;
        List<Object> response = api.locationsRetrieving(countryId, cityId);

        // TODO: test validations
    }

    /**
     * Get all transfers for selected origin, destination, date
     * <p>
     * Get all available transfers. Transfers are retrieved.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void transfersRetrievingTest() throws ApiException {
        Long originId = null;
        Long destinationId = null;
        LocalDate date = null;
        List<Object> response = api.transfersRetrieving(originId, destinationId, date);

        // TODO: test validations
    }

    /**
     * Update user&#39;s profile
     * <p>
     * Update user&#39;s profile
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateUserProfileTest() throws ApiException {
        Long userId = null;
        UserProfileEdition userProfileEdition = null;
        api.updateUserProfile(userId, userProfileEdition);

        // TODO: test validations
    }

    /**
     * Update user&#39;s transfer
     * <p>
     * Update user&#39;s transfer
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateUserTransferTest() throws ApiException {
        Long userId = null;
        Long transferId = null;
        UserTransferEdition userTransferEdition = null;
        api.updateUserTransfer(userId, transferId, userTransferEdition);

        // TODO: test validations
    }

    /**
     * Get user&#39;s profile
     * <p>
     * Get user&#39;s profile. User&#39;s profile is retrieved.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void userProfileRetrievingTest() throws ApiException {
        Long userId = null;
        UserProfile response = api.userProfileRetrieving(userId);

        // TODO: test validations
    }

    /**
     * Get user&#39;s transfer
     * <p>
     * Get user&#39;s transfer. User&#39;s transfer is retrieved.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void userTransferRetrievingTest() throws ApiException {
        Long userId = null;
        Long transferId = null;
        UserTransfer response = api.userTransferRetrieving(userId, transferId);

        // TODO: test validations
    }

    /**
     * Get user&#39;s transfers
     * <p>
     * Get user&#39;s transfers. User&#39;s transfers are retrieved.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void userTransfersRetrievingTest() throws ApiException {
        Long userId = null;
        List<Object> response = api.userTransfersRetrieving(userId);

        // TODO: test validations
    }

}