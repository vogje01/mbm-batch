import Form, {EmptyItem, GroupItem, SimpleItem} from "devextreme-react/form";
import {getFormattedTime} from "../../utils/date-time-util";
import React from "react";

class JobScheduleDetailsAuditing extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: props.currentJobSchedule
        };
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentJobSchedule}>
                    <GroupItem caption={'Auditing'} colSpan={2} colCount={4}>
                        <SimpleItem dataField="id" editorOptions={{readOnly: true}}/>
                        <EmptyItem/>
                        <EmptyItem/>
                        <EmptyItem/>
                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                    editorOptions={{value: getFormattedTime(this.state.currentJobSchedule, 'createdAt'), readOnly: true}}/>
                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                    editorOptions={{value: getFormattedTime(this.state.currentJobSchedule, 'modifiedAt'), readOnly: true}}/>
                    </GroupItem>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobScheduleDetailsAuditing