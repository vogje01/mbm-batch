import React from "react";
import {Form, GroupItem, RequiredRule, SimpleItem, StringLengthRule} from "devextreme-react/form";

class JobDefinitionParamForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinitionParam: props.jobDefinitionParam !== undefined ? props.jobDefinitionParam : {},
            currentJobDefinitionParamType: props.jobDefinitionParam !== undefined ? props.jobDefinitionParam.type : 'STRING'
        }
        this.paramTypes = [
            {label: 'String', type: 'STRING'},
            {label: 'Long', type: 'LONG'},
            {label: 'Double', type: 'DOUBLE'},
            {label: 'Boolean', type: 'BOOLEAN'},
            {label: 'Date', type: 'DATE'}
        ];
        this.onTypeChanged = this.onTypeChanged.bind(this);
    }

    onTypeChanged(e) {
        this.setState({currentJobDefinitionParamType: e.selectedItem.type});
    }

    render() {
        return (
            <React.Fragment>
                <Form formData={this.state.currentJobDefinitionParam}>
                    <GroupItem colCount={3} caption={"Job Definition Parameter Details: " + this.state.currentJobDefinitionParam.keyName}>
                        <SimpleItem dataField="keyName">
                            <RequiredRule/>
                            <StringLengthRule max={256} message="Name must be less than 256 characters."/>
                        </SimpleItem>
                        <SimpleItem
                            dataField={'type'}
                            editorType={'dxSelectBox'}
                            editorOptions={{dataSource: this.paramTypes, valueExpr: 'type', displayExpr: 'label', onSelectedChanged: this.onTypeChanged}}>
                            <RequiredRule/>
                        </SimpleItem>
                        <SimpleItem
                            dataField={'longValue'}
                            editorType={'dxTextBox'}
                            visible={this.state.currentJobDefinitionParamType === 'LONG'}>
                            <RequiredRule/>
                        </SimpleItem>
                        <SimpleItem
                            dataField={'booleanValue'}
                            editorType={'dxCheckBox'}
                            visible={this.state.currentJobDefinitionParamType === 'BOOLEAN'}>
                            <RequiredRule/>
                        </SimpleItem>
                        <SimpleItem
                            dataField={'stringValue'}
                            editorType={'dxTextBox'}
                            visible={this.state.currentJobDefinitionParamType === 'STRING'}>
                            <RequiredRule/>
                        </SimpleItem>
                    </GroupItem>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobDefinitionParamForm;