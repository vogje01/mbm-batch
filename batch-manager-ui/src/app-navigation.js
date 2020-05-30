export const navigation = [
    {
        text: 'Home',
        path: '/home',
        icon: 'home'
    },
    {
        text: 'Execution',
        icon: 'material-icons-outlined ic-dashboard',
        items: [
            {
                text: 'Job Executions',
                path: '/jobexecutions',
                icon: 'material-icons-outlined ic-dashboard'
            },
            {
                text: 'Step Executions',
                path: '/stepexecutions',
                icon: 'material-icons-outlined ic-bookmarks'
            }
        ]
    },
    {
        text: 'Definition',
        icon: 'material-icons-outlined ic-description',
        items: [
            {
                text: 'Job Definition',
                path: '/jobdefinitions',
                icon: 'material-icons-outlined ic-jobdefinition'
            },
            {
                text: 'Job Schedules',
                path: '/jobschedules',
                icon: 'material-icons-outlined ic-alarm'
            },
            {
                text: 'Agents',
                path: '/agents',
                icon: 'material-icons-outlined ic-agent'
            }
        ]
    },
    {
        text: 'Performance',
        icon: 'material-icons-outlined ic-performance',
        items: [
            {
                text: 'Agents',
                path: '/performanceagent',
                icon: 'material-icons-outlined ic-performance'
            },
            {
                text: 'Jobs',
                path: '/performancejob',
                icon: 'material-icons-outlined ic-performance'
            }
        ]
    },
    {
        text: 'Settings',
        icon: 'preferences',
        items: [
            {
                text: 'Users',
                path: '/users',
                icon: 'material-icons-outlined ic-user'
            },
            {
                text: 'User Groups',
                path: '/usergroups',
                icon: 'material-icons-outlined ic-group'
            },
            {
                text: 'Profile',
                path: '/profile',
                icon: 'material-icons-outlined ic-profile'
            }
        ]
    }
];
