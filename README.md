Overview
========

Each scientific data analysis pipeline needs its own workspace to perform the execution. Based on the workspace language, this project can generate a virtual cluster on Clouds and deploys essential software tools of the workspace on the cluster.

Initially, each workspace description is transformed into an intermediate file called Spiceweasel file and then combined with the Chef cookbooks for eventual deployment.

Workspace language
==================

This XML language defines the virtual topology of a computational cluster and specifies the virtual machine¡¯s hardware feature of each computing node as well as the mechanism of distributed resource management.

```
<workspace>
	<provider>Name of Cloud provider</provider>
	<vm_size>Size of a VM</vm_size>
	<vm_number>Number of VMs</vm_number>
	<description>Description of this workspace</description>
	<run_list>
		<package>
			<name>Name of first software tool</name>
			<version>Version of first software tool</version>
		</package>
		<package>
			<name>Name of second software tool</name>
			<version>Version of second software tool</version>
		</package>
	</run_list>
</workspace>
```

Workspace deployment system
===========================

The Workspace deployment system is designed as a web service that receives a workspace description as an input and returns a status report of workspace deployment. Thus, it can be used by anyone who provides a workspace description for deployment purpose. The Workspace deployment system also has a plugin-in mechanism that can cooperate with other Cloud providers.

The keystone of the system is the Chef package that uses 3rd party plugins (e.g. knife-azure, knife-ec2, knife-openstack, etc.), to communicate with various Cloud providers including Windows Azure, Amazon EC2 and Openstack, through a command line interface. Due to the different command-line options of the Chef plugins for bootstrapping VM instances, we introduce a Spiceweasel file to keep these provider-specific invocation stubs. Thus, we need to provide a transformation service for each Cloud provider to convert a general workspace description of our framework into a Cloud-provider-dependent Spiceweasel file.

Wrapping the core chef package and its third-party plugins, we implement a Chef engine that is responsible for communicating with Cloud providers for deploying VM instances with cookbooks. To utilize these Chef engines, we further develop a Deployment Handler that can transfer Spiceweasel files to these Chef engines and receive status of workspace deployment. The Deployment Hander runs as worker threads, each of which is linked to a Chef engine.
