File buildLog = new File(basedir, 'build.log')
String log = buildLog.getText('UTF-8')

// The upload is routed to an unroutable local proxy, so the only acceptable failure is the
// connection attempt - which happens strictly after the HTTP client is constructed.
assert !log.contains('NoClassDefFoundError') && !log.contains('A required class was missing') :
    'runtime ClassRealm failure: a class used by the plugin is not on its runtime classpath'
assert log.contains('Upload to Wago.io failed') :
    'expected the goal to get past HTTP client construction and fail at the network call'

return true
