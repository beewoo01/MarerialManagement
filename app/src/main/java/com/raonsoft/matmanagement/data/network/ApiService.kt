package com.raonsoft.matmanagement.data.network

import com.raonsoft.matmanagement.data.entity.AccountStateEntity
import com.raonsoft.matmanagement.data.entity.CurrentProductContentEntity
import com.raonsoft.matmanagement.data.entity.ItemOfProductsEntity
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("loginAccount")
    suspend fun loginAccount(
        @Field("account_id") account_id : String,
        @Field("account_password") account_password : String,
        @Field("account_isPro") account_isPro : Int
    ) : Response<Int>

    @FormUrlEncoded
    @POST("signUpAccount")
    suspend fun signUpAccount(
        @Field("account_id") account_id : String,
        @Field("account_password") account_password : String,
        @Field("account_ispro") account_ispro : Int,
        @Field("armyunit_name") armyunit_name : String,
        @Field("armyunit_division") armyunit_division : String,
        @Field("armyunit_regiment") armyunit_regiment : String,
        @Field("armyunit_battalion") armyunit_battalion : String,
        @Field("accoundArmyUnit_permission") accoundArmyUnit_permission : Int
    ) : Response<Int>


    @GET("findID")
    suspend fun findID(
        @Query("account_name") account_name : String,
        @Query("accoundArmyUnit_armyunit_idx") accoundArmyUnit_armyunit_idx : Int
    ) : Response<String>


    @GET("findPassword")
    suspend fun findPassword(
        @Query("account_name") account_name : String,
        @Query("accoundArmyUnit_armyunit_idx") accoundArmyUnit_armyunit_idx : Int,
        @Query("account_id") account_id : String

    ) : Response<String>


    @FormUrlEncoded
    @POST("registItem")
    suspend fun registItem(
        @Field("item_name") item_name : String,
        @Field("item_image") item_image : String?,
        @Field("account_idx") account_idx : Int

    ) : Response<Int>

    @FormUrlEncoded
    @POST("updateItem")
    suspend fun updateItem(
        @Field("account_idx") account_idx : Int,
        @Field("item_name") item_name : String,
        @Field("item_image") item_image : String,
        @Field("item_idx") item_idx : Int,
    ) : Response<Int>


    @FormUrlEncoded
    @POST("registProduct")
    suspend fun registProduct(
        @Field("product_name") product_name : String,
        @Field("product_description") product_description : String,
        @Field("product_item_idx") product_item_idx : Int,
        @Field("product_account_idx") product_account_idx : Int,
        @Field("product_taginfo") product_taginfo : String

    ) : Response<Int>


    @FormUrlEncoded
    @POST("updateProduct")
    suspend fun updateProduct(
        @Field("product_account_idx") product_account_idx : Int,
        @Field("product_name") product_name : String,
        @Field("product_description") product_description : String,
        @Field("product_taginfo") product_taginfo : String,
        @Field("product_status") product_status : Int,
        @Field("product_idx") product_idx : Int

    ) : Response<Int>


    @GET("getProductIdxOfTagInfo")
    suspend fun getProductIdxOfTagInfo(
        @Query("product_taginfo") product_taginfo : String,
    ) : Response<Int>



    @FormUrlEncoded
    @POST("provistionProduct")
    suspend fun provistionProduct(
        @Field("provisionInfo_product_idx") provisionInfo_product_idx : Int,
        @Field("provisionInfo_account_idx") provisionInfo_account_idx : Int,
        @Field("provisionInfo_company") provisionInfo_company : String,
        @Field("provisionInfo_platoon") provisionInfo_platoon : String,
        @Field("provisionInfo_user_name") provisionInfo_user_name : String,
        @Field("provisionInfo_default") provisionInfo_default : String

    ) : Response<Int>


    @FormUrlEncoded
    @POST("deleteProduct")
    suspend fun deleteProduct(
        @Field("item_idx") item_idx : Int
    ) : Response<Int>


    @FormUrlEncoded
    @POST("deleteItem")
    suspend fun deleteItem(
        @Field("item_idx") item_idx : Int
    ) : Response<Int>

    @GET("getProductCountOfItems")
    suspend fun getProductCountOfItems(
        @Query("account_idx") account_idx : Int
    ) : Response<List<ItemOfProductsEntity>>



    @GET("getProductOfItemIdx")
    suspend fun getProductOfItemIdx(
        @Query("item_idx") item_idx : Int
    ) : Response<List<CurrentProductContentEntity>>

    @GET("getAccountInfo")
    suspend fun getAccountInfo(
        @Query("account_idx") account_idx : Int
    ) : Response<HashMap<String, Any>>

    @GET("getAccountList")
    suspend fun getAccountList(
        @Query("armyunit_idx") armyunit_idx : Int,
        @Query("permission") permission : Int
    ): Response<List<AccountStateEntity>>

    @GET("getAccountDetailInfo")
    suspend fun getAccountDetailInfo(
        @Query("account_idx") account_idx :Int
    ) : Response<HashMap<String, Any>>

    @FormUrlEncoded
    @POST("updateAccountInfo")
    suspend fun updateAccountInfo(
        @Field("account_idx") account_idx :Int,
        @Field("account_password") account_password :String,
        @Field("account_name") account_name :String,
        @Field("account_profile") account_profile :String?,
        @Field("armyunit_name") armyunit_name :String,
        @Field("armyunit_division") armyunit_division :String,
        @Field("armyunit_regiment") armyunit_regiment :String,
        @Field("armyunit_battalion") armyunit_battalion :String,
    ) : Response<Int>


    @GET("getProductInfoOfTag")
    suspend fun getProductInfoOfTag(
        @Query("product_taginfo") product_taginfo : String
    ) : Response<HashMap<String, Any?>>

    @FormUrlEncoded
    @POST("productReturn")
    suspend fun productReturn(
        @Field("product_idx") product_idx : Int
    ) : Response<Int>

}