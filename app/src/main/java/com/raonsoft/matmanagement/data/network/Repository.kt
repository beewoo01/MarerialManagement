package com.raonsoft.matmanagement.data.network

import com.raonsoft.matmanagement.data.entity.AccountStateEntity
import com.raonsoft.matmanagement.data.entity.CurrentProductContentEntity
import com.raonsoft.matmanagement.data.entity.ItemOfProductsEntity
import com.raonsoft.matmanagement.data.url.RetrofitGenerator
import retrofit2.Response

class Repository : ApiService {

    private val apiService: ApiService = RetrofitGenerator.getApiService()

    override suspend fun loginAccount(
        account_id: String,
        account_password: String,
        account_isPro: Int
    ): Response<Int> = apiService.loginAccount(account_id, account_password, account_isPro)

    override suspend fun signUpAccount(
        account_id: String,
        account_password: String,
        account_ispro: Int,
        armyunit_name: String,
        armyunit_division: String,
        armyunit_regiment: String,
        armyunit_battalion: String,
        accoundArmyUnit_permission: Int
    ): Response<Int> =

        apiService.signUpAccount(
            account_id,
            account_password,
            account_ispro,
            armyunit_name,
            armyunit_division,
            armyunit_regiment,
            armyunit_battalion,
            accoundArmyUnit_permission
        )

    override suspend fun findID(
        account_name: String,
        accoundArmyUnit_armyunit_idx: Int
    ): Response<String> =
        apiService.findID(account_name, accoundArmyUnit_armyunit_idx)

    override suspend fun findPassword(
        account_name: String,
        accoundArmyUnit_armyunit_idx: Int,
        account_id: String
    ): Response<String> =
        apiService.findPassword(account_name, accoundArmyUnit_armyunit_idx, account_id)

    override suspend fun registItem(
        item_name: String,
        item_image: String?,
        account_idx: Int
    ): Response<Int> = apiService.registItem(item_name, item_image, account_idx)

    override suspend fun updateItem(
        account_idx: Int,
        item_name: String,
        item_image: String,
        item_idx: Int
    ): Response<Int> = apiService.updateItem(account_idx, item_name, item_image, item_idx)

    override suspend fun registProduct(
        product_name: String,
        product_description: String,
        product_item_idx: Int,
        product_account_idx: Int,
        product_taginfo: String
    ): Response<Int> =

        apiService.registProduct(
            product_name,
            product_description,
            product_item_idx,
            product_account_idx,
            product_taginfo
        )

    override suspend fun updateProduct(
        product_account_idx: Int,
        product_name: String,
        product_description: String,
        product_taginfo: String,
        product_status: Int,
        product_idx: Int
    ): Response<Int> =

        apiService.updateProduct(
            product_account_idx,
            product_name,
            product_description,
            product_taginfo,
            product_status,
            product_idx
        )

    override suspend fun getProductIdxOfTagInfo(product_taginfo: String): Response<Int> =
        apiService.getProductIdxOfTagInfo(product_taginfo)

    override suspend fun provistionProduct(
        provisionInfo_product_idx: Int,
        provisionInfo_account_idx: Int,
        provisionInfo_company: String,
        provisionInfo_platoon: String,
        provisionInfo_user_name: String,
        provisionInfo_default: String
    ): Response<Int> =

        apiService.provistionProduct(
            provisionInfo_product_idx,
            provisionInfo_account_idx,
            provisionInfo_company,
            provisionInfo_platoon,
            provisionInfo_user_name,
            provisionInfo_default
        )

    override suspend fun deleteProduct(item_idx: Int): Response<Int> =
        apiService.deleteProduct(item_idx)

    override suspend fun deleteItem(item_idx: Int): Response<Int> = apiService.deleteItem(item_idx)

    override suspend fun getProductCountOfItems(account_idx: Int): Response<List<ItemOfProductsEntity>> =
        apiService.getProductCountOfItems(account_idx)

    override suspend fun getProductOfItemIdx(item_idx: Int): Response<List<CurrentProductContentEntity>> =
        apiService.getProductOfItemIdx(item_idx)

    override suspend fun getAccountInfo(account_idx: Int): Response<HashMap<String, Any>> =
        apiService.getAccountInfo(account_idx)

    override suspend fun getAccountList(
        armyunit_idx: Int,
        permission: Int
    ): Response<List<AccountStateEntity>> =
        apiService.getAccountList(armyunit_idx, permission)

    override suspend fun getAccountDetailInfo(account_idx: Int): Response<HashMap<String, Any>> =
        apiService.getAccountDetailInfo(account_idx)


    override suspend fun updateAccountInfo(
        account_idx: Int,
        account_password: String,
        account_name: String,
        account_profile: String?,
        armyunit_name: String,
        armyunit_division: String,
        armyunit_regiment: String,
        armyunit_battalion: String
    ): Response<Int> =
        apiService.updateAccountInfo(
            account_idx = account_idx,
            account_password = account_password,
            account_name = account_name,
            account_profile = account_profile,
            armyunit_name = armyunit_name,
            armyunit_division = armyunit_division,
            armyunit_regiment = armyunit_regiment,
            armyunit_battalion = armyunit_battalion
        )


    override suspend fun getProductInfoOfTag(product_taginfo: String): Response<HashMap<String, Any?>> =
        apiService.getProductInfoOfTag(product_taginfo)

    override suspend fun productReturn(product_idx: Int): Response<Int> =
        apiService.productReturn(product_idx)


}