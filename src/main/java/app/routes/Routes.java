package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    //private DoctorMockRoute doctorMockRoute = new DoctorMockRoute();
    private DoctorRoute doctorRoute = new DoctorRoute();

    public EndpointGroup getApiRoutes() {
        return () ->
        {
            //path("/doctor", doctorMockRoute.getDoctorRoutes());
            path("/doctor", doctorRoute.getDoctorRoutes());

        };
    }
}
