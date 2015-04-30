package thehandsomecoder.com.destinyloadouts;


import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.RequiresCookie;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


/**
 * Created by somalley on 16/04/2015.
 */
@Rest(rootUrl = "https://www.bungie.net/Platform", converters = { MappingJackson2HttpMessageConverter.class })
public interface DestinyService extends RestClientHeaders {
    @Get("/User/GetBungieNetUser/")
    @RequiresHeader({"x-csrf", "X-API-Key"})
    @RequiresCookie({"bungleatk","bungled"})
    String getCurrentUser();
}

