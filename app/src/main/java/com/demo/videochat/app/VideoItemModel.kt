package com.demo.videochat.app

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideoItemModel (

    @Expose
    @SerializedName("type")
    val type: String,

    @Expose
    @SerializedName("features")
    val features: List<Feature>
)

data class Feature (
    @Expose
    @SerializedName("type")
    val type: String,

    @Expose
    @SerializedName("properties")
    val properties: Properties,

    @Expose
    @SerializedName("geometry")
    val geometry: Geometry
)

data class Geometry (
    @Expose
    @SerializedName("type")
    val type: String,

    @Expose
    @SerializedName("coordinates")
    val coordinates: List<Double>
)

data class Properties (

    @Expose
    @SerializedName("title")
    val title: String,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("occurred_at")
    val occurredAt: String,

    @Expose
    @SerializedName("marker-size")
    val markerSize: String,

    @Expose
    @SerializedName("marker-color")
    val markerColor: String,

    @Expose
    @SerializedName("id")
    val id: Long
)