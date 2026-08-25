package net.nfmcpwr.EasyHome;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tools.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubAPIResponse
{
    @JsonProperty("name")
    public String Name;
    
    @JsonProperty("published_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date PublishedAt;
    
    @JsonProperty("assets")
    public List<GitHubAsset> Assets;
    
    @JsonProperty("body")
    public String Summary;
    
    @JsonIgnore
    public boolean IsError;
    
    @JsonIgnore
    public String ErrorMessage;
    
    public GitHubAPIResponse()
    {
    
    }
    
    private static GitHubAPIResponse Error(String errorMessage)
    {
        GitHubAPIResponse r = new GitHubAPIResponse();
        r.IsError = true;
        r.ErrorMessage = errorMessage;
        
        return r;
    }
    
    public static GitHubAPIResponse GetLatestRelease()
    {
        
        OkHttpClient client = new OkHttpClient();
        
        Request request = new Request.Builder()
            .url("https://api.github.com/repos/nfmcpwr/EasyHome/releases/latest")
            .get()
            .build();
        
        try
        {
            Response response = client.newCall(request).execute();
            
            if (response.isSuccessful())
            {
                return new ObjectMapper().readValue(response.body()
                    .string(), GitHubAPIResponse.class);
            }
            else
            {
                return GitHubAPIResponse.Error("Not OK");
            }
        }
        catch (IOException e)
        {
            return GitHubAPIResponse.Error(e.getMessage());
        }
    }
}
