package com.rahmat.codelab.popularmovies.model;

import java.util.List;

/**
 * Created by rahmat on 7/31/2017.
 */

public class MovieTrailer {

    /**
     * id : 321612
     * results : [{"id":"5743a07a9251414c0d000605","iso_639_1":"en","iso_3166_1":"US","key":"c38r-SAnTWM","name":"Official US Teaser Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"589219d3c3a36809660098dd","iso_639_1":"en","iso_3166_1":"US","key":"bgeSXHvPoBI","name":"Lumiere Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"589219e79251412dcd009cea","iso_639_1":"en","iso_3166_1":"US","key":"8w3f-RugY60","name":"Cogsworth Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"589219fc9251412dd40094d0","iso_639_1":"en","iso_3166_1":"US","key":"BZj00cPRmUo","name":"Cadenza Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"58921a0dc3a3686b0a005512","iso_639_1":"en","iso_3166_1":"US","key":"hIoJ0tYJtsU","name":"Garderobe Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"58921a1e9251412dc8009db4","iso_639_1":"en","iso_3166_1":"US","key":"vyBd-UtVlZU","name":"Plumette Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"58921a2dc3a368097000986b","iso_639_1":"en","iso_3166_1":"US","key":"TjI83VLKQ3I","name":"Mrs. Potts Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"58921a46c3a368096a009abd","iso_639_1":"en","iso_3166_1":"US","key":"6CUwMuQIpNk","name":"Gaston Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"58921a55c3a36809680095bb","iso_639_1":"en","iso_3166_1":"US","key":"KSTGuuTqcAk","name":"LeFou Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"58921a649251412dd1009f1c","iso_639_1":"en","iso_3166_1":"US","key":"Si4uWyCGT2U","name":"Maurice Motion Poster","site":"YouTube","size":720,"type":"Clip"},{"id":"58ab719392514158c4004381","iso_639_1":"en","iso_3166_1":"US","key":"nT1VQkTTT7M","name":"Official \"Belle\" Clip","site":"YouTube","size":1080,"type":"Clip"},{"id":"58ac1dd8c3a36849cc011c76","iso_639_1":"en","iso_3166_1":"US","key":"e3Nl_TCQXuw","name":"Official US Final Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"58af68039251411a580011e1","iso_639_1":"en","iso_3166_1":"US","key":"S7ENUYTXlJg","name":"Official Golden Globes TV Spot","site":"YouTube","size":1080,"type":"Teaser"},{"id":"58af68409251411a67001144","iso_639_1":"en","iso_3166_1":"US","key":"OvW_L8sTu5E","name":"Official US Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"58af68a5c3a3682cd0001118","iso_639_1":"en","iso_3166_1":"US","key":"Ow78zp30ioE","name":"Official \"Bringing Beauty To Life\" Featurette","site":"YouTube","size":1080,"type":"Featurette"},{"id":"58b6474392514161290062d3","iso_639_1":"en","iso_3166_1":"US","key":"Sq8vjBg7EWE","name":"Beauty And The Beast (2017) Featurette","site":"YouTube","size":720,"type":"Featurette"},{"id":"58b775b29251416137001f28","iso_639_1":"en","iso_3166_1":"US","key":"MKyp9Tx-NPY","name":"\"Dinner Invitation\" Clip","site":"YouTube","size":1080,"type":"Clip"}]
     */
    private int id;
    private List<TrailerList> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerList> getResults() {
        return results;
    }

    public void setResults(List<TrailerList> results) {
        this.results = results;
    }
}
