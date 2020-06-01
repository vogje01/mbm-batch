package org.apache.logging.log4j.core.layout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class uses log4j2 mixins, in order to add some additional fields like hostname, pid, jobName, jobId etc
 * to the JSON logger used by the manager to save the logs into a single table and still be able to distinguish
 * the logs from the different batch jobs.
 * <p>
 * The batch job is identified by nodeName, jobName and jobId which is the UUID of the job.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.2
 */
public class ExtendedJsonAdapter implements ExtendedJson {

    private static final Logger LOG = LogManager.getLogger(ExtendedJsonAdapter.class);

    private static HashMap<String, Object> mixedFields = new HashMap<>();

    @Override
    public Map<String, Object> getMixedFields() {
        return mixedFields;
    }

    public ExtendedJsonAdapter() {
        try {
            mixedFields.put("hostName", InetAddress.getLocalHost().getHostName());
            mixedFields.put("jobPid", ProcessHandle.current().pid());
        } catch (UnknownHostException e) {
            LOG.warn("Could not get hostname", e);
        }
    }

    public static void addMixedFields(String key, Object value) {
        mixedFields.put(key, value);
    }

    public static void removeMixedFields(String key) {
        mixedFields.remove(key);
    }
}
