/**
 * AWSHelper class with methods for managing AWS EC2 instances.
 * It uses AWS SDK for Java 2
 */

package example;

import java.util.List;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.GroupIdentifier;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RebootInstancesRequest;
import software.amazon.awssdk.services.ec2.model.TerminateInstancesRequest;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;


public class AWSHelper {
    private static final Region REGION        = Region.of("eu-west-1");   // Region name
    private static final String AMI_ID        = "ami-07d9160fa81ccffb5";  // AMI Id
    private static final String INSTANCE_TYPE = "t2.micro";               // Instance Type

    private AWSHelper() {
    }

    /**
     * Describes all EC2 instances associated with an AWS account
     */
    public static void describeInstances() {
        Ec2Client ec2 = Ec2Client.builder()
                .region(REGION)
                .build();
        String nextToken = null;

        try {
            System.out.println("Describing EC2 instances ...");
            do {
                DescribeInstancesRequest request = DescribeInstancesRequest.builder().maxResults(6).nextToken(nextToken).build();
                DescribeInstancesResponse response = ec2.describeInstances(request);

                for (Reservation reservation : response.reservations()) {
                    for (Instance instance : reservation.instances()) {
                        System.out.printf(
                                "Found reservation with id \"%s\", " +
                                        "AMI \"%s\", " +
                                        "type \"%s\", " +
                                        "state \"%s\" " +
                                        "and monitoring state \"%s\"\n",
                                instance.instanceId(),
                                instance.imageId(),
                                instance.instanceType(),
                                instance.state().name(),
                                instance.monitoring().state());
                        List<Tag> tags = instance.tags();
                        System.out.println("      Tags:   " + tags);
                        System.out.println();
                    }
                }
                nextToken = response.nextToken();
            } while (nextToken != null);

        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }


    /**
     * Run (Create) an EC2 instance
     */
    public static String runInstance() {
        Ec2Client ec2 = Ec2Client.builder()
                .region(REGION)
                .build();

        System.out.println("Creating EC2 instance ...");

        RunInstancesRequest runRequest = RunInstancesRequest.builder()
                .imageId(AMI_ID)
                .instanceType(INSTANCE_TYPE)
                .maxCount(1)
                .minCount(1)
                .build();

        RunInstancesResponse response = ec2.runInstances(runRequest);
        String instanceId = response.instances().get(0).instanceId();
        String reservationId = response.reservationId();

        System.out.println("Reservation Id: " + reservationId);
        System.out.println("Instance Id:    " + instanceId);

        // Tag EC2 Instance
        String tagName = "my-instance";
        tagInstance(ec2, instanceId, "Name", tagName);
        System.out.printf("Added Tag: Name with Value: %s\n", tagName);

        return instanceId;
    }


    /**
     * Describes an EC2 instance
     */
    public static void describeInstance(String instanceId) {

        if (instanceId == null || instanceId.isEmpty()) {
            System.out.println("Error: NO Instance.");
            return;
        }

        Ec2Client ec2 = Ec2Client.builder()
                .region(REGION)
                .build();

        System.out.println("Describing EC2 instance ...");

        DescribeInstancesRequest request = DescribeInstancesRequest.builder().instanceIds(instanceId).build();
        DescribeInstancesResponse response = ec2.describeInstances(request);

        List<Reservation> reservations = response.reservations();
        Reservation reservation = reservations.get(0);

        List<Instance> instances = reservation.instances();
        Instance instance = instances.get(0);

        String imageId = instance.imageId();
        String instanceType = instance.instanceTypeAsString();
        String state = instance.state().nameAsString();
        List<GroupIdentifier> groups = instance.securityGroups();
        String privateDnsName = instance.privateDnsName();
        String privateIpAddress = instance.privateIpAddress();
        String publicDnsName = instance.publicDnsName();
        String publicIpAddress = instance.publicIpAddress();
        List<Tag> tags = instance.tags();

        System.out.println("Instance Id:       " + instanceId);
        System.out.println("Image Id:          " + imageId);
        System.out.println("Instance Type:     " + instanceType);
        System.out.println("Security Groups:   " + groups);
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Security Group:    " + groups.get(i));
        }
        System.out.println("State:             " + state);
        System.out.println("Private DNS Name:  " + privateDnsName);
        System.out.println("Private IP Name:   " + privateIpAddress);
        System.out.println("Public DNS Name:   " + publicDnsName);
        System.out.println("Public IP Name:    " + publicIpAddress);
        System.out.println("Tags:              " + tags);
        for (int i = 0; i < tags.size(); i++) {
            System.out.println("Tags:              " + tags.get(i));
        }
        System.out.println();
    }


    /**
     * Start an EC2 instance
     */
    public static void startInstance(String instanceId) {

        if (instanceId == null || instanceId.isEmpty()) {
            System.out.println("Error: NO Instance.");
            return;
        }

        Ec2Client ec2 = Ec2Client.builder()
                .region(REGION)
                .build();
        StartInstancesRequest request = StartInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        System.out.println("Starting EC2 instance ...");
        ec2.startInstances(request);
    }


    /**
     * Stop an EC2 instance
     */
    public static void stopInstance(String instanceId) {

        if (instanceId == null || instanceId.isEmpty()) {
            System.out.println("Error: NO Instance.");
            return;
        }

        Ec2Client ec2 = Ec2Client.builder()
                .region(REGION)
                .build();
        StopInstancesRequest request = StopInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        System.out.println("Stopping EC2 instance ...");
        ec2.stopInstances(request);
    }


    /**
     * Reboot an EC2 instance
     */
    public static void rebootInstance(String instanceId) {

        if (instanceId == null || instanceId.isEmpty()) {
            System.out.println("Error: NO Instance.");
            return;
        }

        Ec2Client ec2 = Ec2Client.builder()
                .region(REGION)
                .build();
        RebootInstancesRequest request = RebootInstancesRequest.builder()
                .instanceIds(instanceId).build();

        System.out.println("Rebooting EC2 instance ...");
        ec2.rebootInstances(request);
    }


    /**
     * Terminate an EC2 instance
     */
    public static void terminateInstance(String instanceId) {

        if (instanceId == null || instanceId.isEmpty()) {
            System.out.println("Error: NO Instance.");
            return;
        }

        Ec2Client ec2 = Ec2Client.builder()
                .region(REGION)
                .build();
        TerminateInstancesRequest request = TerminateInstancesRequest.builder()
                .instanceIds(instanceId).build();

        System.out.println("Terminating EC2 instance ...");
        ec2.terminateInstances(request);
    }


    /**
     * Wait some milliseconds
     */
    private static void wait(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            // swallow
        }
    }


    /**
     * Create a tag attached an EC2 instance
     */
    private static void tagInstance(Ec2Client ec2, String instanceId, String tagName, String value) {

        Tag tag = Tag.builder()
                .key(tagName)
                .value(value)
                .build();

        CreateTagsRequest tagRequest = CreateTagsRequest.builder()
                .resources(instanceId)
                .tags(tag)
                .build();

        try {
            ec2.createTags(tagRequest);
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }


    /**
     * Create some tags attached EC2 instances
     */
    private static void tagResources(Ec2Client ec2, List<String> resources, List<Tag> tags) {
        // Create a tag request.
        CreateTagsRequest tagRequest = CreateTagsRequest.builder()
                .resources(resources)
                .tags(tags)
                .build();

        // Try to tag the tag request submitted
        try {
            ec2.createTags(tagRequest);
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}
