import React from 'react';
import {Button} from "devextreme-react";
import FisPage from "../../components/FisPage";
import Form, {Item} from "devextreme-react/form";
import {Popup} from "devextreme-react/popup";
import {updateItem} from "../../components/ServerConnection";
import {getLastPingTime, getLastStartTime} from "../../util/DateTimeUtil";

class AgentDetails extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentAgent: props.currentAgent,
        };
        this.saveAgent = this.saveAgent.bind(this);
    }

    saveAgent() {
        updateItem(this.state.currentAgent._links.update.href, this.state.currentAgent, 'agentDto')
            .then((data) => {
                this.state.currentAgent = data;
                this.props.closePopup();
            })
    }

    render() {
        return (
            <React.Fragment>
                <Popup
                    id={'agentDetailPopup'}
                    className={'popup'}
                    visible={true}
                    dragEnabled={true}
                    closeOnOutsideClick={true}
                    showTitle={true}
                    title={'Agent Details: ' + (this.props.currentAgent.nodeName)}
                    height={'auto'}
                    minWidth={500}
                    minHeight={480}
                    maxWidth={1200}
                    maxHeight={1200}
                    resizeEnabled={true}
                    onHiding={this.props.closePopup}>
                    <Form
                        id={'form'}
                        formData={this.state.currentAgent}
                        labelLocation={'top'}
                        minColWidth={200}
                        colCount={2}>
                        <Item dataField={'id'} editorOptions={{disabled: true}}/>
                        <Item dataField={'nodeName'}/>
                        <Item dataField={'lastStart'} editorType={'dxTextBox'}
                              editorOptions={{disabled: true, value: getLastStartTime(this.state.currentAgent)}}
                              dataType={'datetime'}/>
                        <Item dataField={'lastPing'} editorType={'dxTextBox'}
                              editorOptions={{disabled: true, value: getLastPingTime(this.state.currentAgent)}}
                              dataType={'datetime'}/>
                        <Item dataField={'active'} editorType={'dxCheckBox'}/>
                    </Form>

                    <Button
                        width={90}
                        text={'Apply'}
                        type={'success'}
                        stylingMode={'contained'}
                        onClick={this.saveAgent}
                        style={{float: 'right', margin: '5px 10px 0 0'}}/>
                </Popup>
            </React.Fragment>
        );
    }
}

export default AgentDetails;