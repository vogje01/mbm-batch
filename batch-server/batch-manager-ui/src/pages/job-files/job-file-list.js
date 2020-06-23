import React from "react";
import FileManager, {Permissions} from "devextreme-react/file-manager";
import CustomFileSystemProvider from "devextreme/file_management/custom_provider";
import {EndTimer, StartTimer} from "../../utils/method-timer";
import {handleResponse, initGet} from "../../utils/server-connection";

export function provider() {
    return new CustomFileSystemProvider({
        getItems: function (pathInfo) {
            StartTimer();
            let url = process.env.REACT_APP_SCHEDULER_URL + 'library?command=getItems';
            return fetch(url, initGet())
                .then(response => {
                    return handleResponse(response)
                })
                .then(data => {
                    return data
                })
                .finally(() => EndTimer());
        }
    })
};

class JobFileList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <FileManager
                fileSystemProvider={provider()}
                currentPath={'.'}>
                <Permissions
                    // create={true}
                    // copy={true}
                    // move={true}
                    // delete={true}
                    // rename={true}
                    // upload={true}
                    download={true}>
                </Permissions>
            </FileManager>
        );
    }
}

export default JobFileList
