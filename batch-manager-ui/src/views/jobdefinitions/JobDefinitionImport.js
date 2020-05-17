import React from 'react';
import {Popup} from "devextreme-react/popup";
import {Button, TextArea} from "devextreme-react";
import {errorMessage, infoMessage} from "../../util/MessageUtil";
import {EndTimer} from "../../util/MethodTimer";

class JobDefinitionImport extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinitions: []
        };
    }

    onImportTextAreaChanged(e) {
        this.setState({currentJobDefinitions: JSON.parse(e.value)})
    }

    importJobDefinitions() {
        fetch(localStorage.getItem('baseUrl') + '/api/jobdefinitions/import',
            {
                method: 'PUT',
                body: JSON.stringify(this.state.currentJobDefinitions),
                headers: {'Content-Type': 'application/json'}
            })
            .then(response => {
                if (response.status === 200) {
                    this.setState({currentJobDefinitions: [], importPopupVisible: false})
                    infoMessage('Job definitions imported');
                }
            })
            .catch((error) => {
                errorMessage('Job definitions import error: ' + error.message);
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
                    title={'Job Definition Import'}
                    dragEnabled={true}
                    resizeEnabled={true}
                    closeOnOutsideClick={true}>
                    <TextArea
                        width={'100%'}
                        height={'90%'}
                        onValueChanged={this.onImportTextAreaChanged}/>
                    <Button
                        width={90}
                        text={'Import'}
                        type={'normal'}
                        stylingMode={'contained'}
                        onClick={this.importJobDefinitions}
                        style={{float: 'right', margin: '5px 10px 0 0'}}/>
                </Popup>
            </React.Fragment>
        );
    }
}

export default JobDefinitionImport;