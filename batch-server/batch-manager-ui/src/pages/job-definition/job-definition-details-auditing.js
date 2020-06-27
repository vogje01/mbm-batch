import React from "react";
import Form, {EmptyItem, SimpleItem} from "devextreme-react/form";
import {getFormattedTime} from "../../utils/date-time-util";

class JobDefinitionDetailsAuditing extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: props.currentJobDefinition
        };
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentJobDefinition} colCount={4}>
                    <SimpleItem dataField="id" editorOptions={{readOnly: true}}/>
                    <EmptyItem/>
                    <EmptyItem/>
                    <EmptyItem/>
                    <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentJobDefinition, 'createdAt'), readOnly: true}}/>
                    <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentJobDefinition, 'modifiedAt'), readOnly: true}}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobDefinitionDetailsAuditing