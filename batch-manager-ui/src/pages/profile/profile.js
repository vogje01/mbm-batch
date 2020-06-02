import React from 'react';
import './profile.scss';
import Form, {EmailRule, PatternRule, RequiredRule, SimpleItem, StringLengthRule} from 'devextreme-react/form';
import {updateItem} from "../../utils/server-connection";
import themes from "devextreme/ui/themes";
import {getCreatedAt, getModifiedAt} from "../../utils/date-time-util";

const themesList = [
    'material.blue.dark.compact',
    'material.blue.light.compact',
    'material.orange.dark.compact',
    'material.orange.light.compact',
    'material.lime.dark.compact',
    'material.lime.light.compact'
];

class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            passwordChanged: false,
            user: JSON.parse(localStorage.getItem('user'))
        };
        this.phonePattern = /^\s*\+[0-9]{2,3}\s*-?\s*\d{3}-?\s*[0-9 ]+$/;
        this.handleSubmit = this.handleSubmit.bind(this);
        this.passwordChanged = this.passwordChanged.bind(this);
        this.themeSelectionChanged = this.themeSelectionChanged.bind(this);
    }

    themeSelectionChanged(e) {
        console.log(window.localStorage.getItem("dx-theme"));
        window.localStorage.setItem("dx-theme", e.value);
        console.log(window.localStorage.getItem("dx-theme"));
        themes.current(e.value);
    }

    passwordChanged(e) {
        this.setState({passwordChanged: true});
    }

    handleSubmit(e) {
        e.event.preventDefault();
        let user = this.state.user;
        user.passwordChanged = this.state.passwordChanged;
        updateItem(process.env.REACT_APP_API_URL + 'users/' + user.id + '/update', user, 'userDto')
            .then((data) => {
                this.setState({
                    user: data
                });
                localStorage.setItem('user', JSON.stringify(data));
                themes.current(data.theme);
            })
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Profile</h2>

                <div className={'content-block dx-card responsive-paddings'}>
                    <div className={'form-avatar'}>
                        <img alt={''} src={this.state.user._links.avatar.href}/>
                    </div>
                    <span>{this.state.user.description}</span>
                </div>

                <div className={'content-block dx-card responsive-paddings'}>
                    <form key={'user'}>
                        <Form
                            readOnly={false}
                            formData={this.state.user}
                            validationGroup="customerData">
                            <SimpleItem itemType="group" colCount={4} caption={"User Details: " + this.state.user.userId}>
                                <SimpleItem id={'userId'} dataField="userId" colSpan={2}>
                                    <RequiredRule message="UserId is required"/>
                                    <StringLengthRule min={5} max={7} message="UserId must be exactly 7 characters long."/>
                                </SimpleItem>
                                <SimpleItem dataField="password" editorType={"dxTextBox"}
                                            editorOptions={{mode: "password", onValueChanged: this.passwordChanged}}>
                                    <RequiredRule message="Password is required"/>
                                </SimpleItem>
                                <SimpleItem dataField="firstName">
                                    <RequiredRule message="First name is required"/>
                                </SimpleItem>
                                <SimpleItem dataField="lastName">
                                    <RequiredRule message="Last name is required"/>
                                </SimpleItem>
                                <SimpleItem dataField="email">
                                    <EmailRule message="Email is invalid"/>
                                </SimpleItem>
                                <SimpleItem dataField="phone">
                                    <PatternRule message="The phone must have a correct phone format" pattern={this.phonePattern}/>
                                </SimpleItem>
                                <SimpleItem dataField="theme"
                                            editorType={"dxSelectBox"}
                                            editorOptions={{
                                                dataSource: themesList,
                                                onValueChanged: this.themeSelectionChanged
                                            }}/>
                                <SimpleItem dataField="description" editorType="dxTextArea" colSpan={4} editorOptions={{height: 100}}/>
                            </SimpleItem>
                            <SimpleItem itemType="group" colCount={4} caption={"Auditing"}>
                                <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                            editorOptions={{value: getCreatedAt(this.state.user), readOnly: true}}/>
                                <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                            editorOptions={{value: getModifiedAt(this.state.user), readOnly: true}}/>
                            </SimpleItem>
                            <SimpleItem editorType={'dxButton'} colSpan={1} editorOptions={{
                                horizontalAlignment: 'left',
                                text: 'Save',
                                type: 'success',
                                useSubmitBehavior: false,
                                onClick: this.handleSubmit
                            }}/>
                        </Form>
                    </form>
                </div>
            </React.Fragment>
        );
    };
}

export default Profile;

