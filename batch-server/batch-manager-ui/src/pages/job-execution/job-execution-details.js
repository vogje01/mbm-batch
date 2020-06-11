import React from "react";
import NavigationList from "./job-execution-navigation-list";
import {Drawer} from "devextreme-react";


class JobExecutionDetailsPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: props.data
        };
    }

    render() {
        return (
            <React.Fragment>
                <Drawer
                    opened={false}
                    openedStateMode={'shrink'}
                    position={'shrink'}
                    component={NavigationList}
                    revealMode={'top'}
                    closeOnOutsideClick={true}>
                    <div id="content">
                        {"This is a text"}
                    </div>
                </Drawer>
            </React.Fragment>
        );
    }
}

export default JobExecutionDetailsPage