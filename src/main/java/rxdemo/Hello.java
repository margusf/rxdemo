package rxdemo;

import lombok.Data;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static rx.Observable.*;
import static rx.observables.MathObservable.averageInteger;
import static rxdemo.MovieDb.*;

public class Hello {

    public static void main(String[] args) throws Exception {
        System.out.println("main.java.Hello");

        Observable<MovieWithAge> result = parallelMap(getMovies(),
                movie -> {
                    System.out.println(
                            "working(" + Thread.currentThread()
                                    + ") -> " + movie.getTitle());
                    return new MovieWithAge(movie.getTitle(),
                            getAverageAge(movie));
                });
        result.toBlocking().forEach(
                mwa -> {
                    System.out.println("RESULT: " + mwa.getTitle()
                            + ", " + mwa.getAge());
                });
    }

    private static int getAverageAge(MovieInfo movie) {
        Observable<ActorInfo> cast = from(getCast(movie));
        Observable<ActorDetails> details = parallelMap(
                cast,
                MovieDb::getActorDetails);
        Observable<Integer> ages = parallelMap(
                details,
                actorDetails ->
                        actorDetails.getAge(movie.getReleaseDate()));
        return averageInteger(filterNotNull(ages))
                .onErrorReturn(t -> 0)
                .toBlocking()
                .firstOrDefault(null);
    }

    private static Observable<Integer> filterNotNull(Observable<Integer> obs) {
        return obs.filter(i -> i != null);
    }

    private static Observable<MovieInfo> getMovies()
            throws Exception {
        return from(MovieDb.getMovies()).filter(
                m -> m.getReleaseDate() != null);
    }

    private static <X, Y> Observable<Y> parallelMap(Observable<X> obs,
            Func1<X, Y> func) {
        return obs.parallel(item -> item.map(func), Schedulers.newThread());
    }

    private static <X, Y> Observable<Y> map(Observable<X> obs,
            Func1<X, Y> func) {
        return obs.map(func);
    }

    @Data
    private static class MovieWithAge {
        private final String title;
        private final int age;
    }
}
