import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class SmartCityApp {
    private static List<PassengerTrip> synthesizeTrips(LocalDateTime now) {
        Random r = new Random();
        return IntStream.rangeClosed(1, 20).mapToObj(i -> {
            String pid = "P" + i;
            String route = r.nextBoolean() ? "B1" : "M1";
            TransportType mode = r.nextBoolean() ? TransportType.BUS : TransportType.METRO;
            double fare = mode == TransportType.BUS ? 20 + r.nextInt(10) : 25 + r.nextInt(5);
            boolean peak = r.nextBoolean();
            return new PassengerTrip(pid, route, mode, fare, peak, now.minusMinutes(r.nextInt(60)));
        }).collect(Collectors.toList());
    }
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        List<Schedule> busA = Arrays.asList(
                new Schedule("B1", now.plusMinutes(10), now.plusMinutes(40), 20.0, false),
                new Schedule("B1", now.plusMinutes(30), now.plusMinutes(60), 22.0, true),
                new Schedule("B2", now.plusMinutes(5),  now.plusMinutes(35), 18.0, false)
        );
        List<Schedule> metroA = Arrays.asList(
                new Schedule("M1", now.plusMinutes(3),  now.plusMinutes(23), 25.0, true),
                new Schedule("M1", now.plusMinutes(13), now.plusMinutes(33), 25.0, true),
                new Schedule("M2", now.plusMinutes(8),  now.plusMinutes(28), 24.0, false)
        );

        List<TransportService> services = Arrays.asList(
                new BusService("BUS-7", busA),
                new MetroService("METRO-Blue", metroA)
        );

        Predicate<Schedule> nextHour = s -> !s.getDeparture().isBefore(now) && s.getDeparture().isBefore(now.plusHours(1));
        Comparator<Schedule> byFareThenTime = Comparator.comparingDouble(Schedule::getFare).thenComparing(Schedule::getDeparture);
        FareCalculator calculator = (schedule, multiplier) -> Math.round(schedule.getFare() * multiplier * 100.0) / 100.0;

        System.out.println("\n=== 1) Booking: Filter & Sort ===");
        List<Schedule> candidateSchedules = services.stream()
                .flatMap(svc -> svc.findSchedules(nextHour).stream())
                .sorted(byFareThenTime)
                .toList();
        candidateSchedules.stream().limit(5).forEach(System.out::println);

        System.out.println("\nSample dynamic fares:");
        candidateSchedules.stream().limit(5).forEach(s -> {
            double multiplier = s.isPeak() ? 1.2 : 1.0;
            System.out.printf("%s -> %.2f%n", s, calculator.calculateFare(s, multiplier));
        });

        System.out.println("\n=== 2) Revenue Analytics ===");
        List<PassengerTrip> trips = synthesizeTrips(now);

        trips.stream().collect(Collectors.groupingBy(PassengerTrip::getRouteId))
                .forEach((route, ts) -> System.out.println(route + " : " + ts.size()));

        DoubleSummaryStatistics fareStats = trips.stream().collect(Collectors.summarizingDouble(PassengerTrip::getFare));
        System.out.println("Fare summary: " + fareStats);

        Map<TransportType, Double> revenueByMode =
                trips.stream().collect(Collectors.groupingBy(PassengerTrip::getMode, Collectors.summingDouble(PassengerTrip::getFare)));
        System.out.println("Revenue by mode: " + revenueByMode);
    }
}

enum TransportType { BUS, METRO, TAXI, AMBULANCE }
enum ServiceStatus { ACTIVE, INACTIVE }

class Schedule {
    private final String route;
    private final LocalDateTime dep, arr;
    private final double fare;
    private final boolean peak;
    Schedule(String route, LocalDateTime dep, LocalDateTime arr, double fare, boolean peak) {
        this.route=route; this.dep=dep; this.arr=arr; this.fare=fare; this.peak=peak;
    }
    public LocalDateTime getDeparture(){ return dep; }
    public double getFare(){ return fare; }
    public boolean isPeak(){ return peak; }
    public String toString(){ return route+" dep="+dep.getMinute()+" fare="+fare+" peak="+peak; }
}

final class PassengerTrip {
    final String passengerId, routeId;
    final TransportType mode; final double fare; final boolean peak;
    final LocalDateTime timestamp;
    PassengerTrip(String pid, String route, TransportType mode, double fare, boolean peak, LocalDateTime ts) {
        this.passengerId=pid; this.routeId=route; this.mode=mode; this.fare=fare; this.peak=peak; this.timestamp=ts;
    }
    String getRouteId(){ return routeId; }
    TransportType getMode(){ return mode; }
    double getFare(){ return fare; }
    boolean isPeak(){ return peak; }
}

interface TransportService {
    String getId();
    TransportType getType();
    List<Schedule> getSchedules();
    default List<Schedule> findSchedules(Predicate<Schedule> filter) {
        return getSchedules().stream().filter(filter).toList();
    }
}

abstract class BaseService implements TransportService {
    private final String id; private final TransportType type; private final List<Schedule> schedules;
    BaseService(String id, TransportType type, List<Schedule> schedules){ this.id=id; this.type=type; this.schedules=schedules; }
    public String getId(){ return id; }
    public TransportType getType(){ return type; }
    public List<Schedule> getSchedules(){ return schedules; }
}
class BusService extends BaseService { BusService(String id, List<Schedule> schedules){ super(id,TransportType.BUS,schedules);} }
class MetroService extends BaseService { MetroService(String id, List<Schedule> schedules){ super(id,TransportType.METRO,schedules);} }

interface FareCalculator {
    double calculateFare(Schedule schedule, double multiplier);
}


