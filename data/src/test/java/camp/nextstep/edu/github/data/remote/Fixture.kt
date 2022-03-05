package camp.nextstep.edu.github.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

internal class Fixture {
    private val mockServer = MockWebServer()

    init {
        mockServer.enqueue(
            MockResponse()
                .setBody(File("src/test/resources/github.json").readText())
        )
    }

    private val gitHubDataSource = Retrofit
        .Builder()
        .baseUrl(mockServer.url(""))
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi
                    .Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .build()
        .create(GitHubDataSource::class.java)

    companion object {
        fun mockGitHubDataSource(): GitHubDataSource = Fixture().gitHubDataSource
    }
}
