import React from 'react';
import './profile.scss';
import Form, {EmailRule, EmptyItem, PatternRule, RequiredRule, SimpleItem, StringLengthRule} from 'devextreme-react/form';
import {getItem, updateItem} from "../../utils/server-connection";
import themes from "devextreme/ui/themes";
import {getFormattedTime} from "../../utils/date-time-util";
import {FileUploader} from "devextreme-react";

const themesList = [
    'material.blue.dark.compact',
    'material.blue.light.compact',
    'material.orange.dark.compact',
    'material.orange.light.compact',
    'material.lime.dark.compact',
    'material.lime.light.compact'
];

const dateFormatList = [
    {label: 'de', value: 'DE'},
    {label: 'en-gb', value: 'ENGB'},
    {label: 'en-us', value: 'ENUS'}
];

const numberFormatList = [
    {label: 'de', value: 'DE'},
    {label: 'en-gb', value: 'ENGB'},
    {label: 'en-us', value: 'ENUS'}
];

class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            passwordChanged: false,
            user: JSON.parse(localStorage.getItem('user')),
            selectedFiles: []
        };
        this.phonePattern = /^\s*\+[0-9]{2,3}\s*-?\s*\d{3}-?\s*[0-9 ]+$/;
        this.handleSubmit = this.handleSubmit.bind(this);
        this.passwordChanged = this.passwordChanged.bind(this);
        this.themeSelectionChanged = this.themeSelectionChanged.bind(this);
        this.dateFormatValueChanged = this.dateFormatValueChanged.bind(this);
        this.numberFormatValueChanged = this.numberFormatValueChanged.bind(this);
    }

    themeSelectionChanged(e) {
        themes.ready(() => {
            this.setState({});
            themes.current(e.value);
        });
    }

    passwordChanged(e) {
        this.setState({passwordChanged: true});
    }

    dateFormatValueChanged(e) {
        localStorage.setItem('dateTimeFormat', e.value);
    }

    numberFormatValueChanged(e) {
        localStorage.setItem('numberFormat', e.value);
    }

    onUploaded(e) {
        getItem(process.env.REACT_APP_API_URL + 'users/' + this.state.user.id)
            .then((data) => {
                this.setState({
                    user: data
                });
                localStorage.setItem('user', JSON.stringify(data));
            })
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
                    <div className="widget-container" style={{marginLeft: '-7px'}}>
                        <FileUploader multiple={false} accept={'images/*'} uploadMode={'instantly'}
                                      uploadHeaders={{'Authorization': 'Bearer ' + localStorage.getItem('webToken')}}
                                      uploadUrl={process.env.REACT_APP_API_URL + 'users/avatar/' + this.state.user.id} onUploaded={this.onUploaded.bind(this)}
                                      labelText={''} uploadedMessage={'You need to logout/login for the changes to take effect!'}/>
                        <div className="content" style={{display: this.state.selectedFiles.length > 0 ? 'block' : 'none'}}>
                            <div>
                                <h4>Selected Files</h4>
                                {
                                    this.state.selectedFiles.map((file, i) => {
                                        return <div className="selected-item" key={i}>
                                            <span>{`Name: ${file.name}`}<br/></span>
                                            <span>{`Size ${file.size}`}<br/></span>
                                            <span>{`Type ${file.size}`}<br/></span>
                                            <span>{`Last Modified Date: ${file.lastModifiedDate}`}</span>
                                        </div>;
                                    })
                                }
                            </div>
                        </div>
                    </div>
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
                                <SimpleItem
                                    dataField={'dateTimeFormat'}
                                    editorType={'dxSelectBox'}
                                    editorOptions={{
                                        dataSource: dateFormatList,
                                        valueExpr: 'value',
                                        displayExpr: 'label',
                                        onValueChanged: this.dateFormatValueChanged
                                    }}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <SimpleItem
                                    dataField={'numberFormat'}
                                    editorType={'dxSelectBox'}
                                    editorOptions={{
                                        dataSource: numberFormatList,
                                        valueExpr: 'value',
                                        displayExpr: 'label',
                                        onValueChanged: this.numberFormatValueChanged
                                    }}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <EmptyItem/>
                                <EmptyItem/>
                                <SimpleItem dataField="description" editorType="dxTextArea" colSpan={4} editorOptions={{height: 100}}/>
                            </SimpleItem>
                            <SimpleItem itemType="group" colCount={4} caption={"Auditing"}>
                                <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                            editorOptions={{value: getFormattedTime(this.state.user, 'createdAt'), readOnly: true}}/>
                                <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                            editorOptions={{value: getFormattedTime(this.state.user, 'modifiedAt'), readOnly: true}}/>
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

