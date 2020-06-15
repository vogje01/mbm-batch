import React from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import {SingleCard} from './layouts';
import {ChangePasswordForm, CreateAccountForm, LoginForm, ResetPasswordForm} from './components';

export default function () {
    return (
        <React.Fragment>
            <div className={'content-block'} style={{width: '100%', height: '100%'}}>
                <h2 align={'center'}><b>Momentum Batch Management</b></h2>
                <h3 align={'center'}>Distributed Batch Management</h3>
                <div>
                    <Switch>
                        <Route exact path='/login'>
                            <SingleCard title="Sign In">
                                <LoginForm/>
                            </SingleCard>
                        </Route>New
                        <Route exact path='/create-account'>
                            <SingleCard title="Sign Up">
                                <CreateAccountForm/>
                            </SingleCard>
                        </Route>
                        <Route exact path='/reset-password'>
                            <SingleCard
                                title="Reset Password"
                                description="Please enter the email address that you used to register, and we will send you an email with a link to reset your password.">
                                <ResetPasswordForm/>
                            </SingleCard>
                        </Route>
                        <Route exact path='/change-password/:recoveryCode'>
                            <SingleCard title="Change Password">
                                <ChangePasswordForm/>
                            </SingleCard>
                        </Route>
                        <Redirect to={'/login'}/>
                    </Switch>
                </div>
            </div>
        </React.Fragment>
    );
}
