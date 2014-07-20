/**
 * Connection to movie database:
 * http://docs.themoviedb.apiary.io/
 */
class MovieDb {
    static void getMovies() throws Exception {
        // GEThttp://api.themoviedb.org/3/discover/movie
        System.out.println(
                HttpHelper.doGet("http://api.themoviedb.org/3/discover/movie?api_key=0835385142505861236af08685de3c1f"));
    }
}
