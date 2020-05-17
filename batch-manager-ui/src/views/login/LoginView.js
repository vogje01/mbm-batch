import React from "react";
import Form, {ButtonItem, SimpleItem} from "devextreme-react/form";
import * as Globalize from "devextreme/localization";
import {loginRequest} from "../../components/ServerConnection";

const organizations = ['EXT', 'HLAG'];

class LoginView extends React.Component {
    constructor(props) {
        super(props);
        const search = props.location.search;
        const params = new URLSearchParams(search);
        this.state = {
            authRequest: {
                userId: params.get('userId'),
                password: params.get('password'),
                orgUnit: params.get('orgUnit') ? params.get('orgUnit') : 'HLAG',
            }
        };
        localStorage.clear();
        this.handleSubmit = this.handleSubmit.bind(this);
        Globalize.locale('de');
    }

    handleSubmit(event) {
        event.preventDefault();
        let basicAuthentication = new Buffer(this.state.authRequest.userId + ':' + this.state.authRequest.password + ':' + this.state.authRequest.orgUnit).toString('base64');
        loginRequest(basicAuthentication, this.state.authRequest);
    }

    validateForm() {
        return this.state.authRequest.userId.length > 0 && this.state.authRequest.password.length > 0;
    }

    render() {
        return (
            <React.Fragment>
                <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', height: '200px'}}>
                    <form onSubmit={this.handleSubmit}>
                        <Form formData={this.state.authRequest} showColonAfterLabel={true} showValidationSummary={true}>
                            <SimpleItem dataField={'orgUnit'} editorType={'dxSelectBox'}
                                        editorOptions={{
                                            items: organizations,
                                            value: this.state.orgUnit
                                        }}/>
                            <SimpleItem dataField={'userId'} editorType={'dxTextBox'}/>
                            <SimpleItem dataField={'password'} editorType={'dxTextBox'}
                                        editorOptions={{mode: 'password'}}/>
                            <ButtonItem horizontalAlignment={'left'}
                                        buttonOptions={{text: 'Login', type: 'success', useSubmitBehavior: true}}/>
                        </Form>
                    </form>
                </div>
            </React.Fragment>
        );
    }
}

export default LoginView;