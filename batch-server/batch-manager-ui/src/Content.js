import React from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import appInfo from './app-info';
import routes from './app-routes';
import {SideNavOuterToolbar as SideNavBarLayout} from './layouts';
import {Footer} from './components';

export default function () {
    return (
        <SideNavBarLayout title={appInfo.title}>
            <Switch>
                {routes.map(({path, component}) => (
                    <Route
                        exact
                        key={path}
                        path={path}
                        component={component}
                    />
                ))}
                <Redirect to={'/home'}/>
            </Switch>
            <Footer>
                Copyright © 2020 Momentum Inc. All trademarks or registered trademarks are property of their respective owners.
            </Footer>
        </SideNavBarLayout>
    );
}
