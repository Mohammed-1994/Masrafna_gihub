package com.example.masrafna.api

import com.example.masrafna.data.AboutCondeitionsResponse
import com.example.masrafna.data.auth.request.*
import com.example.masrafna.data.auth.response.*
import com.example.masrafna.data.auth.response.ResetPasswordResponse
import com.example.masrafna.data.models.*
import com.example.masrafna.data.profile.body.UpdatePassword
import com.example.masrafna.data.profile.response.ProfileResponse
import com.example.masrafna.data.profile.response.UpdatePassResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

interface MainInterface {

    /**** Auth methods Start ***/

    @POST("auth/signup")
    fun signup(
        @Body signUpBody: SignUpBody,
    ): Single<SignupResponse>


    @POST("auth/signup/verify_otp")
    fun verifyOTP(
        @Body otp: VerifyOTPBody
    ): Single<VerifyOTPResponse>

    @GET("auth/signup/resend_otp")
    fun resendOTPCode(): Single<VerifyOTPResponse>


    @POST("auth/login")
    fun login(
        @Body loginBody: LoginBody,
    ): Single<LoginResponse>


    @POST("auth/logout")
    fun logout(): Single<LogoutResponse>


    @POST("auth/password/request")
    fun resetPassword(
        @Body phone: ChangPassword
    ): Single<ResetPasswordResponse>


    @POST("auth/password/verify_otp")
    fun resetPasswordVerifyOTP(
        @Body changPassVerifyOTP: ChangPassVerifyOTP
    ): Single<ResetPasswordVerifyOTP>

    @POST("auth/password/resend_otp")
    fun resetPasswordResendVerifyOTP(
        @Body phone: ChangPassword
    ): Single<ResetPasswordVerifyOTP>


    @POST("auth/change_password")
    fun changePassword(
        @Body password: ChagnePasswordBody
    ): Single<ChangePasswordResponse>


    @GET("profile")
    fun getProfile(

    ): Single<ProfileResponse>

    /****  End Auth methods  ***/


    /****  Start Profile methods  ***/

    @POST("profile")
    fun updateProfileBody(
        @Body profile: RequestBody
    ): Single<ProfileResponse>


    @POST("password/update")
    fun updatePassword(
        @Body updatePassword: UpdatePassword
    ): Single<UpdatePassResponse>


    @GET("notifications")
    fun getNotifications(): Single<NotificationModel>


    /****  End Profile methods  ***/


    /****  About/Conditions methods  ***/

    @GET("conditions")
    fun getConditions(): Single<AboutCondeitionsResponse>

    @GET("about")
    fun getInfo(): Single<AboutCondeitionsResponse>


    /**** News ***////
    @GET("news")
    fun getAllNews(@Query("page") page: Int): Single<NewsListModel>

    @GET("news/{id}")
    fun getNewsDetails(@Path("id") id: String): Single<NewsDetails>


    /**** Articles ***////
    @GET("articles")
    fun getAllArticles(@Query("page") page: Int): Single<ArticleListModel>

    @GET("article/{id}")
    fun getArticleDetails(@Path("id") id: String): Single<ArticleDetails>


    /**** Localizations ***////
    @GET("localizations/banks")
    fun getLocalizations(@Query("page") page: Int): Single<Localizations>

    @GET("localization")
    fun getBankLocalization(@Query("bank_id") bankId: String): Single<BankLocalizationModel>


    /**** Start Banks methods ***////
    @GET("banks")
    fun getAllBanks(): Single<BankListModel>

    @GET("banks/filter")
    fun searchBanksByName(
        @Query("name") filter: String
    ): Single<BankListModel>

    @GET("bank")
    fun getBankDetails(
        @Query("bank_id") id: String
    ): Single<BankDetails>


    // bank services

    @GET("bank/branches")
    fun getBranches(
        @Query("bank_id") bankId: String,
        @Query("governate") governate: Int
    ): Single<BankBranche>


    @GET("bank/pos")
    fun getPOSs(
        @Query("bank_id") bankId: String,
        @Query("governate") governate: Int
    ): Single<BankBranche>


    @GET("bank/atms")
    fun getATMs(
        @Query("bank_id") bankId: String,
        @Query("governate") governate: Int
    ): Single<BankBranche>


    // favorite banks api
    @POST("favorates")
    fun addToFavorite(@Body id: AddToFavoriteModel): Single<Unit>

    @DELETE("favorates/{bank_id}")
    fun removeFromFavorite(@Path("bank_id") bank_id: String): Single<Unit>
    /**** Ends Banks methods ***/


    /**** Starts Loans methods ***/

    @GET("loans")
    fun getLoansByTypeAndBankType(
        @Query("type") loanType: Int,
        @Query("bank_type") bankType: Int,
        @Query("page") page: Int
    ): Single<LoanListResponse>


    @GET("loan/banks")
    fun getAvailableBankList(
        @Query("loan_id") loanType: String
    ): Single<AvailableBankList>


    @GET("loan")
    fun getLoanDetailsByBankId(
        @Query("loan_id") loanId: String,
        @Query("bank_id") bankId: String,
    ): Single<LoanDetailsByBank>


    @GET("bank/loan")
    fun getBankLoansByLoanType(
        @Query("loan_type") loanType: Int,
        @Query("bank_id") bankId: String,
        @Query("page") page: Int
    ): Single<LoanListResponse>


    @GET("bank/funding")
    fun getFundingByTypeBankPage(
        @Query("funding_type") fundingType: Int,
        @Query("bank_id") bankId: String,
        @Query("page") page: Int
    ): Single<LoanListResponse>

    @GET("bank/loan/details")
    fun getLoanDetails(
        @Query("loan_id") loanId: String,
    ): Single<LoanDetailsByBank>


    @GET("fundings")
    fun getFundingBbyType(
        @Query("type") fundingType: Int,
        @Query("page") page: Int
    ): Single<LoanListResponse>


    /**** End Loans methods ***/


    /**** Starts Online methods ***/

    @GET("online_services/banks")
    fun getOnlineServiceBanks(@Query("page") page: Int): Single<OnlineServiceModel>

    @GET("online_service")
    fun getOnlineServiceDetails(@Query("bank_id") id: String)
            : Single<OnlineServiceDetails>

}