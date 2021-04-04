package schosearch.backend.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "http.props")
@Validated
public class HttpProps {
    String communitiesUrl;
    String keywordsUrl;
    String papersUrl;
    String papersAuthName;
    String papersAuthPass;

    public String getCommunitiesUrl() {
        return communitiesUrl;
    }

    public void setCommunitiesUrl(String communitiesUrl) {
        this.communitiesUrl = communitiesUrl;
    }

    public String getKeywordsUrl() {
        return keywordsUrl;
    }

    public void setKeywordsUrl(String keywordsUrl) {
        this.keywordsUrl = keywordsUrl;
    }

    public String getPapersUrl() {
        return papersUrl;
    }

    public void setPapersUrl(String papersUrl) {
        this.papersUrl = papersUrl;
    }

    public String getPapersAuthName() {
        return papersAuthName;
    }

    public void setPapersAuthName(String papersAuthName) {
        this.papersAuthName = papersAuthName;
    }

    public String getPapersAuthPass() {
        return papersAuthPass;
    }

    public void setPapersAuthPass(String papersAuthPass) {
        this.papersAuthPass = papersAuthPass;
    }
}
