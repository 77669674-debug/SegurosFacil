package com.example.segurosfacil.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import okhttp3.HttpUrl.Companion.toHttpUrl

object RetrofitClient {

    private val bootstrapClient = OkHttpClient.Builder().build()

    private val dns = DnsOverHttps.Builder().client(bootstrapClient)
        .url("https://dns.google/dns-query".toHttpUrl())
        .bootstrapDnsHosts(
            InetAddress.getByName("8.8.8.8"),
            InetAddress.getByName("8.8.4.4")
        )
        .build()

    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("apikey", SupabaseConfig.API_KEY)
            .addHeader("Authorization", "Bearer ${SupabaseConfig.API_KEY}")
            .addHeader("Content-Type", "application/json")
            .addHeader("Prefer", "return=representation")
            .build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .dns(dns)
        .addInterceptor(headerInterceptor)
        .build()

    val instance: Retrofit = Retrofit.Builder()
        .baseUrl(SupabaseConfig.BASE_URL + "rest/v1/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}