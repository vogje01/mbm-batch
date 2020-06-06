import React from 'react';
import {TextArea} from "devextreme-react";

import {errorMessage, infoMessage} from "../../utils/message-util";

class JobDefinitionExport extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinitions: []
        };
    }

    componentDidMount() {
        fetch(process.env.REACT_APP_API_URL + 'jobdefinitions/export', {
            headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken')},
            method: 'GET'
        })
            .then(response => response.json())
            .then((data) => {
                this.setState({currentJobDefinitions: data})
                infoMessage('Job definitions exported');
            })
            .catch((error) => {
                errorMessage('Job definitions export error: ' + error.message);
            });
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Definition Export</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <TextArea
                            autoResizeEnabled={true}
                            value={JSON.stringify(this.state.currentJobDefinitions, null, 4)}/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobDefinitionExport;