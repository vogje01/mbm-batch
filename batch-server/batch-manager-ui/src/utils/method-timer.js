import React, {Component} from 'react';
import {Subject} from 'rxjs'
import {filter} from 'rxjs/operators'

// The Main Subject/Stream to be listened on.
const timerSubject = new Subject();
const publish = (topic, data) => timerSubject.next({topic, data});
export const StartTimer = () => publish('Timer', {startTimer: window.performance.now()});
export const EndTimer = () => publish('Timer', {endTimer: window.performance.now()});

class MethodTimer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            topic: 'Timer',
            startTimer: 0,
            endTimer: 0
        };
        this.unsub = timerSubject
            .pipe(filter(f => f.topic === this.state.topic))
            .subscribe(s => {
                if (s.data.startTimer !== undefined) {
                    this.state.startTimer = s.data.startTimer;
                } else if (s.data.endTimer !== undefined) {
                    this.state.endTimer = s.data.endTimer;
                }
            });
    }

    componentWillUnmount() {
        this.unsub.unsubscribe()
    }

    render() {
        if (this.state.endTimer === undefined || this.state.endTimer < this.state.startTimer) {
            return null;
        }
        const elapsed = Math.trunc(this.state.endTimer - this.state.startTimer);
        if (elapsed > 0) {
            return (<span>&nbsp;[{elapsed}ms]</span>)
        }
        return null;
    }
}

export default MethodTimer;