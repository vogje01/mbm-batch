import React from 'react';
import './profile.scss';
import Form, {EmailRule, EmptyItem, PatternRule, RequiredRule, SimpleItem, StringLengthRule} from 'devextreme-react/form';
import {updateItem} from "../../utils/server-connection";
import themes from "devextreme/ui/themes";

const themesList = [
    {name: 'material.blue.dark.compact'},
    {name: 'material.blue.light.compact'},
    {name: 'material.orange.dark.compact'},
    {name: 'material.orange.light.compact'},
    {name: 'material.lime.dark.compact'},
    {name: 'material.lime.light.compact'}
];

class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: JSON.parse(localStorage.getItem('user'))
        };
        this.phonePattern = /^\s*\+[0-9]{2,3}\s*-?\s*\d{3}-?\s*[0-9 ]+$/;
        this.handleSubmit = this.handleSubmit.bind(this);
        this.themeSelectionChanged = this.themeSelectionChanged.bind(this);
    }

    themeSelectionChanged(e) {
        themes.current(e.data);
    }

    handleSubmit(e) {
        e.event.preventDefault();
        updateItem(process.env.REACT_APP_API_URL + 'users/' + this.state.user.id + '/update', this.state.user, 'userDto');
        this.setState({});
    }

    render() {
        return (

            <React.Fragment>
                <h2 className={'content-block'}>Profile</h2>

                <div className={'content-block dx-card responsive-paddings'}>
                    <div className={'form-avatar'}>
                        <img alt={''} src={this.state.user.links[0].href}/>
                    </div>
                    <span>{this.state.user.description}</span>
                </div>

                <div className={'content-block dx-card responsive-paddings'}>
                    {process.env.REACT_APP_API_URL}
                    <form key={'user'}>
                        <Form
                            readOnly={false}
                            formData={this.state.user}
                            validationGroup="customerData">
                            <SimpleItem itemType="group" colCount={4} colSpan={4} caption={"User Details: " + this.state.user.userId}>
                                <SimpleItem id={'userId'} dataField="userId" colSpan={2}>
                                    <RequiredRule message="UserId is required"/>
                                    <StringLengthRule min={5} max={7} message="UserId must be exactly 7 characters long."/>
                                </SimpleItem>
                                <SimpleItem dataField="password" editorType={"dxTextBox"} editorOptions={{mode: "password"}} colSpan={2}>
                                    <RequiredRule message="Password is required"/>
                                </SimpleItem>
                                <SimpleItem dataField="firstName" colSpan={2}>
                                    <RequiredRule message="First name is required"/>
                                </SimpleItem>
                                <SimpleItem dataField="lastName" colSpan={2}>
                                    <RequiredRule message="Last name is required"/>
                                </SimpleItem>
                                <SimpleItem dataField="email" colSpan={2}>
                                    <EmailRule message="Email is invalid"/>
                                </SimpleItem>
                                <SimpleItem dataField="phone" colSpan={2}>
                                    <PatternRule message="The phone must have a correct phone format" pattern={this.phonePattern}/>
                                </SimpleItem>
                                <SimpleItem dataField="theme"
                                            colSpan={2}
                                            editorType={"dxSelectBox"}
                                            editorOptions={{
                                                dataSource: themesList,
                                                displayExpr: 'name',
                                                valueExpr: 'name',
                                                onSelectionChanged: this.themeSelectionChanged
                                            }}/>
                                <EmptyItem colSpan={2}/>
                                <SimpleItem dataField="description" editorType="dxTextArea" colSpan={4} editorOptions={{height: 100}}/>
                                <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="createdAt" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="modifiedAt" editorOptions={{readOnly: true}}/>
                            </SimpleItem>
                            <SimpleItem editorType={'dxButton'} editorOptions={{
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

