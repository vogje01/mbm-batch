import {withNavigationWatcher} from './contexts/navigation';
import {
    AgentGroupListPage,
    AgentListPage,
    HomePage,
    JobDefinitionExport,
    JobDefinitionImport,
    JobDefinitionListPage,
    JobExecutionListPage,
    JobFileList,
    JobFileUpload,
    JobGroupListPage,
    JobScheduleListPage,
    LogListPage,
    PerformanceAgentPage,
    PerformanceJobPage,
    ProfilePage,
    StepExecutionListPage,
    UserGroupListPage,
    UserListPage
} from './pages';

const routes = [
    {path: '/jobexecutions/:status', component: JobExecutionListPage},
    {path: '/jobexecutions', component: JobExecutionListPage},
    {path: '/stepexecutions', component: StepExecutionListPage},
    {path: '/logs', component: LogListPage},
    {path: '/jobdefinitions', component: JobDefinitionListPage},
    {path: '/jobgroups', component: JobGroupListPage},
    {path: '/jobdefinitionexport', component: JobDefinitionExport},
    {path: '/jobdefinitionimport', component: JobDefinitionImport},
    {path: '/jobfileupload', component: JobFileUpload},
    {path: '/jobfilesystem', component: JobFileList},
    {path: '/jobschedules', component: JobScheduleListPage},
    {path: '/agents', component: AgentListPage},
    {path: '/agentgroups', component: AgentGroupListPage},
    {path: '/performanceagent', component: PerformanceAgentPage},
    {path: '/performancejob', component: PerformanceJobPage},
    {path: '/profile', component: ProfilePage},
    {path: '/users', component: UserListPage},
    {path: '/usergroups', component: UserGroupListPage},
    {path: '/home', component: HomePage}
];

export default routes.map(route => {
    return {
        ...route,
        component: withNavigationWatcher(route.component)
    };
});
