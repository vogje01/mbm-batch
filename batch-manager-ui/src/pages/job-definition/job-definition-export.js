import React from 'react';
import {Popup} from "devextreme-react/popup";
import {TextArea} from "devextreme-react";
import {errorMessage, infoMessage} from "../../utils/message-util";
import {EndTimer} from "../../utils/method-timer";

class JobDefinitionExport extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    onShowExportPopup() {
        fetch(localStorage.getItem('baseUrl') + '/api/jobdefinitions/export')
            .then(response => response.json())
            .then((data) => {
                this.setState({currentJobDefinitions: data})
                infoMessage('Job definitions exported');
            })
            .catch((error) => {
                errorMessage('Job definitions export error: ' + error.message);
            })
            .finally(() => {
                EndTimer();
            });
    }

    render() {
        return (
            <React.Fragment>
                <Popup
                    showTitle={true}
                    title={'Job Definition Export'}
                    dragEnabled={true}
                    resizeEnabled={true}
                    closeOnOutsideClick={true}
                    onShowing={this.onShowExportPopup}>
                    <TextArea
                        width={'100%'}
                        height={'100%'}
                        value={JSON.stringify(this.state.currentJobDefinitions, null, 4)}/>
                </Popup>
            </React.Fragment>
        );
    }
}

export default JobDefinitionExport;