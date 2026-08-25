package net.nfmcpwr.EasyHome;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubAsset
{
    @JsonProperty("name")
    public String FileName;
    
    @JsonProperty("size")
    public long Size;
    
    @JsonProperty("digest")
    public String Digest;
    
    @JsonProperty("browser_download_url")
    public String DownloadUrl;
    
    public GitHubAsset()
    {
    
    }
}
