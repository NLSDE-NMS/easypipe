package org.greenpipe.workspace.util;

public class Parameters {
	/**
	 * Configuration file path
	 */
	public static final String CONFIGURATION_FILE_PATH = "src/configuration/config.xml";

	/**
	 * Parameters for generating YAML file
	 */
	public static final String REPORT_STATUS_LOCATION = "templates/status.stg";
	public static final String REPORT_STATUS_TAG = "status";
	public static final String REPORT_WORKSPACES_BEGIN_TAG = "workspaces_begin";
	public static final String REPORT_WORKSPACES_STATUS_TAG = "workspaces_status";
	public static final String REPORT_WORKSPACES_END_TAG = "workspaces_end";
	public static final String YAML_DEPLOY_TEMPLATE_FILE = "templates/deploy.stg";
	public static final String YAML_COOKBOOK_TAG = "cookbooks";
	public static final String YAML_NODES_TAG = "nodes";
	public static final String YAML_KNIFE_TAG = "knife";
	// For Azure
	public static final String YAML_AZURE_CREATE_TAG = "azure_create";
	public static final String YAML_AZURE_BOOTSTRAP_TAG = "azure_bootstrap";
	public static final String YAML_AZURE_DELETE_TAG = "azure_delete";
	
	/**
	 * Cookbooks for initialization
	 */
	public static final String COOKBOOK_INIT_ROOT = "init_root";
	public static final String RECIPE_INIT_ROOT = "init_root::default";
	public static final String COOKBOOK_CATEGORY_PACKAGE = "package";
	public static final String COOKBOOK_CATEGORY_ENVIRONMENT = "environment";
	
	/**
	 * Parameters for Hadoop Installation
	 */
	public static final String COOKBOOK_JAVA = "java";
	public static final String COOKBOOK_HADOOP = "hadoop";
	public static final String RECIPE_DEFAULT = "hadoop::default";
	public static final String RECIPE_SSH_PUBLIC_KEYS = "hadoop::ssh_public_keys";
	public static final String RECIPE_AUTHORIZED_NODES = "hadoop::authorized_nodes";
	public static final String RECIPE_SETUP_HOSTS = "hadoop::setup_hosts";
	public static final String RECIPE_SETUP_HADOOP = "hadoop::setup_hadoop";
	public static final String RECIPE_SETUP_MASTER = "hadoop::setup_master";
	public static final String RECIPE_START_HADOOP = "hadoop::start_hadoop";
	
	/**
	 * Parameters for Torque Installation
	 */
	public static final String COOKBOOK_YUM_EPEL = "yum-epel";
	public static final String COOKBOOK_YUM = "yum";
	public static final String COOKBOOK_TORQUE = "torque";
	public static final String RECIPE_HEAD_NODE = "torque::head_node";
	public static final String RECIPE_COMPUTE_NODE = "torque::compute_node";
	
	/**
	 * Parameters of user information
	 */
	public static final String CLUSTERNAME = "workcluster";
	public static final String NORMAL_USERNAME = "jinchao";
	public static final String ROOT_USERNAME = "root";
	public static final String PASSWORD = "SAASworkflow123";
	
	/**
	 * If a workspace is a default workspace to run jobs
	 */
	public static final String DEFAULT_WORKSPACE_YES = "YES";
	public static final String DEFAULT_WORKSPACE_NO = "NO";
	
	
//	/**
//	 * Runtime status
//	 */
//	public static final int CHEF_ENGINE_EXECUTE_SUCCESSFUL = 0;
//	
//	public static final int STATUS_CREATE_SUCCESSFUL = 101;
//	public static final int STATUS_BOOTSTRAP_SUCCESSFUL = 102;
//	public static final int STATUS_DELETE_SUCCESSFUL = 103;
//	public static final int STATUS_LIST_SUCCESSFUL = 104;
//	public static final int STATUS_START_SUCCESSFUL = 105;
//	public static final int STATUS_STOP_SUCCESSFUL = 106;
//	
//	public static final int STATUS_YAML_SERVICE_NOT_FOUND = 200;
//	public static final String MESSAGE_YAML_SERVICE_NOT_FOUND = "YAML service not found for this cloud provider";
//	
//	public static final int STATUS_CREATE_FAILED = 201;
//	public static final int STATUS_BOOTSTRAP_FAILED = 202;
//	public static final int STATUS_DELETE_FAILED = 203;
//	public static final int STATUS_LIST_FAILED = 204;
//	public static final int STATUS_START_FAILED = 205;
//	public static final int STATUS_STOP_FAILED = 206;
//	/**
//	 * States and messages for workspace
//	 */
//	/*
//	 * ------------------------------------------ preparation --------------------------------------------
//	 */
//	public static final String WORKSPACE_STATE_PARSE_SUCCESSFUL = "parse::ok";
//	public static final String WORKSPACE_STATE_PARSE_FAILED = "parse::error";
//	public static final String WORKSPACE_STATE_LOGIN_SUCCESSFUL = "login::ok";
//	public static final String WORKSPACE_STATE_LOGIN_FAILED = "login::error";
//	
//	/*
//	 * ------------------------------------------ Create --------------------------------------------
//	 */
//	// State
//	public static final String WORKSPACE_STATE_CREATE_NEW = "create::new";
//	public static final String WORKSPACE_STATE_CREATE_QUEUED = "create::queued";
//	public static final String WORKSPACE_STATE_CREATE_INITIALIZING = "create::initializing";
//	public static final String WORKSPACE_STATE_CREATE_ESTABLISHED = "create::established";
//	public static final String WORKSPACE_STATE_CREATE_ERROR = "create::error";
//	// Message
//	public static final String WORKSPACE_MESSAGE_CREATE_NEW = "new create-workspace comes";
//	public static final String WORKSPACE_MESSAGE_CREATE_QUEUED = "add workspace to create-queue";
//	public static final String WORKSPACE_MESSAGE_CREATE_INITIALIZING = "begin to create workspace";
//	public static final String WORKSPACE_MESSAGE_CREATE_ESTABLISHED = "create-workspace has been established";
//	public static final String WORKSPACE_MESSAGE_CREATE_ERROR = "something is wrong during creating workspace";
//	
//	/*
//	 * ------------------------------------------ Bootstrap --------------------------------------------
//	 */
//	// State
//	public static final String WORKSPACE_STATE_BOOTSTRAP_NEW = "bootstrap::new";
//	public static final String WORKSPACE_STATE_BOOTSTRAP_QUEUED = "bootstrap::queued";
//	public static final String WORKSPACE_STATE_BOOTSTRAP_INITIALIZING = "bootstrap::initializing";
//	public static final String WORKSPACE_STATE_BOOTSTRAP_ESTABLISHED = "bootstrap::established";
//	public static final String WORKSPACE_STATE_BOOTSTRAP_ERROR = "bootstrap::error";
//	// Message
//	public static final String WORKSPACE_MESSAGE_BOOTSTRAP_NEW = "new bootstrap-workspace comes";
//	public static final String WORKSPACE_MESSAGE_BOOTSTRAP_QUEUED = "add workspace to bootstrap-queue";
//	public static final String WORKSPACE_MESSAGE_BOOTSTRAP_INITIALIZING = "begin to bootstrap workspace";
//	public static final String WORKSPACE_MESSAGE_BOOTSTRAP_ESTABLISHED = "bootstrap-workspace has been established";
//	public static final String WORKSPACE_MESSAGE_BOOTSTRAP_ERROR = "something is wrong during bootstrapping workspace";
//	
//	/*
//	 * ------------------------------------------ Delete --------------------------------------------
//	 */
//	// State
//	public static final String WORKSPACE_STATE_DELETE_NEW = "delete::new";
//	public static final String WORKSPACE_STATE_DELETE_QUEUED = "delete::queued";
//	public static final String WORKSPACE_STATE_DELETE_INITIALIZING = "delete::initializing";
//	public static final String WORKSPACE_STATE_DELETE_ESTABLISHED = "delete::destroyed";
//	public static final String WORKSPACE_STATE_DELETE_ERROR = "delete::error";
//	// Message
//	public static final String WORKSPACE_MESSAGE_DELETE_NEW = "new delete-workspace comes";
//	public static final String WORKSPACE_MESSAGE_DELETE_QUEUED = "add workspace to delete-queue";
//	public static final String WORKSPACE_MESSAGE_DELETE_INITIALIZING = "begin to delete workspace";
//	public static final String WORKSPACE_MESSAGE_DELETE_ESTABLISHED = "delete-workspace has been destroyed";
//	public static final String WORKSPACE_MESSAGE_DELETE_ERROR = "something is wrong during deleting workspace";
//
//	/*
//	 * ------------------------------------------ List & DRM--------------------------------------------
//	 */
//	public static final String WORKSPACE_STATE_HADOOP_UNKNOWN = "unknown";
//	public static final String WORKSPACE_MESSAGE_CREATE_UNKNOWN = "unknown state for this workspace";
//	public static final String WORKSPACE_STATE_DRM_INITIALIZING = "drm::initializing";
//	public static final String WORKSPACE_STATE_DRM_ERROR = "drm::error";
//	public static final String WORKSPACE_STATE_DRM_ESTABLISHED = "drm::established";
//	
//	/*
//	 * ------------------------------------------ Start --------------------------------------------
//	 */
//	// State
//	public static final String WORKSPACE_STATE_START_NEW = "start::new";
//	public static final String WORKSPACE_STATE_START_QUEUED = "start::queued";
//	public static final String WORKSPACE_STATE_START_INITIALIZING = "start::initializing";
//	public static final String WORKSPACE_STATE_START_ESTABLISHED = "start::established";
//	public static final String WORKSPACE_STATE_START_ERROR = "start::error";
//	// Message
//	public static final String WORKSPACE_MESSAGE_START_NEW = "new start-workspace comes";
//	public static final String WORKSPACE_MESSAGE_START_QUEUED = "add workspace to start-queue";
//	public static final String WORKSPACE_MESSAGE_START_INITIALIZING = "begin to start workspace";
//	public static final String WORKSPACE_MESSAGE_START_ESTABLISHED = "start-workspace has been established";
//	public static final String WORKSPACE_MESSAGE_START_ERROR = "something is wrong during starting workspace";
//	
//	/*
//	 * ------------------------------------------ Stop --------------------------------------------
//	 */
//	// State
//	public static final String WORKSPACE_STATE_STOP_NEW = "stop::new";
//	public static final String WORKSPACE_STATE_STOP_QUEUED = "stop::queued";
//	public static final String WORKSPACE_STATE_STOP_INITIALIZING = "stop::initializing";
//	public static final String WORKSPACE_STATE_STOP_ESTABLISHED = "stop::established";
//	public static final String WORKSPACE_STATE_STOP_ERROR = "stop::error";
//	// Message
//	public static final String WORKSPACE_MESSAGE_STOP_NEW = "new stop-workspace comes";
//	public static final String WORKSPACE_MESSAGE_STOP_QUEUED = "add workspace to stop-queue";
//	public static final String WORKSPACE_MESSAGE_STOP_INITIALIZING = "begin to stop workspace";
//	public static final String WORKSPACE_MESSAGE_STOP_ESTABLISHED = "stop-workspace has been established";
//	public static final String WORKSPACE_MESSAGE_STOP_ERROR = "something is wrong during stopping workspace";
//
//	/**
//	 * States and messages for virtual machines
//	 */
//	// States for VM
//	public static final String VM_STATE_INITIALIZING = "initializing";
//	public static final String VM_STATE_BOOTSTRAPPING = "bootstrapping";
//	public static final String VM_STATE_STARTING = "starting";
//	public static final String VM_STATE_ESTABLISHED = "established";
//	public static final String VM_STATE_STOPPING = "stopping";
//	public static final String VM_STATE_STOPED = "stoped";
//	public static final String VM_STATE_DELETING = "deleting";
//	public static final String VM_STATE_DELETED = "deleted";
//	public static final String VM_STATE_ERROR = "error";
}
