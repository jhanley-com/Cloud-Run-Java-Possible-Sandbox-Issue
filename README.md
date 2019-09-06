# Cloud-Run-Java-Possible-Sandbox-Issue
Temporary repository to report a possible sandbox issue

The problem appears to be with the "anapsix/alpine-java" base image.

The error reported in Stackdriver is: "Application failed to start: Failed to create init process: Failed to load java: no such file or directory"

Switching to the base image "maven:3.5-jdk-8-alpine" works.
