import Form, {EmptyItem, PatternRule, RequiredRule, SimpleItem, StringLengthRule} from "devextreme-react/form";
import React from "react";
import {getItem} from "../../utils/server-connection";

class JobDefinitionDetailsMain extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: props.currentJobDefinition,
            jobGroups: [],
        };
        this.versionPattern = /^\s*\d+\.\d+\.\d+\s*$/;
    }

    componentDidMount() {
        getItem(process.env.REACT_APP_MANAGER_URL + 'jobgroups?page=0&size=-1&sortBy=name&sortDir=asc')
            .then((data) => {
                this.setState({jobGroups: data._embedded.jobGroupDtoes})
            });
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentJobDefinition}
                    colCount={5}>
                    <SimpleItem dataField="label">
                        <StringLengthRule max={256} message="Labels must be less than 256 characters."/>
                    </SimpleItem>
                    <SimpleItem dataField="name">
                        <RequiredRule/>
                        <StringLengthRule max={256} message="Name must be less than 256 characters."/>
                    </SimpleItem>
                    <SimpleItem dataField="jobVersion">
                        <RequiredRule/>
                        <StringLengthRule min={5} max={32} message="Version must be less than 32 characters."/>
                        <PatternRule pattern={this.versionPattern} message="Version must have correct format."/>
                    </SimpleItem>
                    <SimpleItem
                        dataField={'jobGroupId'}
                        editorType={'dxSelectBox'}
                        editorOptions={{dataSource: this.state.jobGroups, valueExpr: 'id', displayExpr: 'name'}}>
                        <RequiredRule/>
                    </SimpleItem>
                    <SimpleItem dataField="active" editorType={"dxCheckBox"}/>
                    <SimpleItem dataField="fileSize" editorType={"dxTextBox"} editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="fileHash" editorType={"dxTextBox"} editorOptions={{readOnly: true}}/>
                    <EmptyItem/>
                    <EmptyItem/>
                    <EmptyItem/>
                    <SimpleItem dataField="description" colSpan={2} editorType={'dxTextArea'} editorOptions={{height: 90}}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobDefinitionDetailsMain