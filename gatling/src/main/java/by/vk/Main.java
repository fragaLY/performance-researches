package by.vk;

public class Main {
    public static void main(String[] args) {
        GatlingPropertiesBuilder props = new GatlingPropertiesBuilder()
                .resourcesDirectory(IDEPathHelper.gradleResourcesDirectory.toString())
                .resultsDirectory(IDEPathHelper.resultsDirectory.toString())
                .binariesDirectory(IDEPathHelper.gradleBinariesDirectory.toString());

        Gatling.fromMap(props.build());
    }
}