import {withNavigationWatcher} from './contexts/navigation';
import {
  AgentListPage,
  DisplayDataPage,
  HomePage,
  JobDefinitionListPage,
  JobExecutionListPage,
  JobScheduleListPage,
  PerformancePage,
  ProfilePage,
  StepExecutionListPage,
  UserGroupListPage,
  UserListPage
} from './pages';

const routes = [
  {
    path: '/jobexecutions',
    component: JobExecutionListPage
  },
  {
    path: '/stepexecutions',
    component: StepExecutionListPage
  },
  {
    path: '/jobdefinitions',
    component: JobDefinitionListPage
  },
  {
    path: '/jobschedules',
    component: JobScheduleListPage
  },
  {
    path: '/agents',
    component: AgentListPage
  },
  {
    path: '/performance',
    component: PerformancePage
  },
  {
    path: '/display-data',
    component: DisplayDataPage
  },
  {
    path: '/profile',
    component: ProfilePage
  },
  {
    path: '/users',
    component: UserListPage
  },
  {
    path: '/usergroups',
    component: UserGroupListPage
  },
  {
    path: '/home',
    component: HomePage
  }
];

export default routes.map(route => {
  return {
    ...route,
    component: withNavigationWatcher(route.component)
  };
});
