package com.chavesgu.scan;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class ScanViewFactory extends PlatformViewFactory {
    @NonNull private final BinaryMessenger messenger;
    @NonNull private final Context context;
    @NonNull private final Activity activity;
    private ActivityPluginBinding activityPluginBinding;

    ScanViewFactory(@NonNull BinaryMessenger messenger, @NonNull Context context, @NonNull Activity activity, @NonNull ActivityPluginBinding activityPluginBinding) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
        this.context = context;
        this.activity = activity;
        this.activityPluginBinding = activityPluginBinding;
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        // args を型安全に Map<String, Object> へ変換（unchecked 警告なし）
        final Map<String, Object> creationParams;

        if (args instanceof Map) {
            // ワイルドカードで一旦受けてから、キーを String 化してコピー
            Map<?, ?> raw = (Map<?, ?>) args;
            Map<String, Object> tmp = new HashMap<>();
            for (Map.Entry<?, ?> e : raw.entrySet()) {
                // key が null の可能性は低いが、念のため String.valueOf で安全に文字列化
                tmp.put(String.valueOf(e.getKey()), e.getValue());
            }
            creationParams = tmp;
        } else {
            // パラメータなし／未知型のときも安全に処理
            creationParams = Collections.emptyMap();
        }

        return new ScanPlatformView(
                messenger,
                this.context,
                this.activity,
                this.activityPluginBinding,
                viewId,
                creationParams
        );
    }
}
