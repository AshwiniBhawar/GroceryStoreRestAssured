package com.qa.grocerystoreproducts.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.ProductsDetails;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UpdateAProductTest extends BaseTest {
    @Test
    public void updateAProductTest(){
        //Create cart
        Response newCartresponse=restClient.post(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS,null,null,  AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertTrue(Boolean.parseBoolean(newCartresponse.jsonPath().getString("created")));
        String cartId=newCartresponse.jsonPath().getString("cartId");

        //Add a product
        ProductsDetails.Items productDetails= ProductsDetails.Items.builder().productId(8753).quantity(1).build();
        Response postRes = restClient.post(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS+"/"+cartId+GROCERY_STORE_ADD_ITEM_ENDPOINTS, productDetails,null, null,  AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertTrue(postRes.statusLine().contains("Created"));
        int itemId=postRes.jsonPath().getInt("itemId");

        //Validate the product details
        Response getRes=restClient.get(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS+"/"+cartId+GROCERY_STORE_ADD_ITEM_ENDPOINTS,null, null,  AuthType.NO_AUTH, ContentType.JSON);
        List<Integer> productIdsBeforeUpdate = getRes.jsonPath().getList("productId");
        List<Integer> itemIdsBeforeUpdate = getRes.jsonPath().getList("id");
        List<Integer> itemQuantBeforeUpdate = getRes.jsonPath().getList("quantity");

        Assert.assertEquals(productIdsBeforeUpdate.get(0), productDetails.getProductId());
        Assert.assertEquals(itemIdsBeforeUpdate.get(0), itemId);
        Assert.assertEquals(itemQuantBeforeUpdate.get(0), productDetails.getQuantity());

        //Update the quantity
        ProductsDetails.Items quantityDetails= ProductsDetails.Items.builder().quantity(3).build();

        Response patchRes=restClient.patch(GROCERY_STORE_BASEURL_REDIRECTION, GROCERY_STORE_CARTS_ENDPOINTS+"/"+cartId+"/items/"+itemId, quantityDetails, null, null,AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertTrue(patchRes.statusLine().contains("No Content"));

        //Validate the product details with updated quantity
        Response getResAfterUpdate=restClient.get(GROCERY_STORE_BASEURL_REDIRECTION,GROCERY_STORE_CARTS_ENDPOINTS+"/"+cartId+GROCERY_STORE_ADD_ITEM_ENDPOINTS,null, null,  AuthType.NO_AUTH, ContentType.JSON);
        List<Integer> productIdsAfterUpdate = getResAfterUpdate.jsonPath().getList("productId");
        List<Integer> itemIdsAfterUpdate = getResAfterUpdate.jsonPath().getList("id");
        List<Integer> itemQuantAfterUpdate = getResAfterUpdate.jsonPath().getList("quantity");

        Assert.assertEquals(productIdsAfterUpdate.get(0), productDetails.getProductId());
        Assert.assertEquals(itemIdsAfterUpdate.get(0), itemId);
        Assert.assertEquals(itemQuantAfterUpdate.get(0), quantityDetails.getQuantity());

    }

}
